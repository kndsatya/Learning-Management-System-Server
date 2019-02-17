package com.example.whiteboardsp19.services;

import com.example.whiteboardsp19.model.Course;
import com.example.whiteboardsp19.model.Lesson;
import com.example.whiteboardsp19.model.Module;
import com.example.whiteboardsp19.model.Topic;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class TopicService {

  private CourseService courseService = new CourseService();

  @PostMapping("/api/lesson/{lid}/topic")
  public List<Topic> createTopic(@PathVariable("lid") Integer lessonId, @RequestBody Topic topic,
                                 HttpSession session) {

    List<Course> courses = courseService.findAllCourses(session);
    if (courses.size() != 0) {
      for (Course course : courses) {
        List<Module> modules = course.getModules();
        for (Module module : modules) {
          for(Lesson lesson:module.getLessons()) {
            if (lesson.getId().equals(lessonId)) {
              List<Topic> topics = lesson.getTopics();
              Random r = new Random();
              topic.setId(r.nextInt(Integer.MAX_VALUE));
              topic.setWidgets(new ArrayList<>());
              topics.add(topic);
              return topics;
            }
          }
        }
      }
    }
    return new ArrayList<>();
  }

  @GetMapping("/api/lesson/{lid}/topic")
  public List<Topic> findAllTopics(@PathVariable("lid") Integer lessonId, HttpSession session) {

    List<Course> courses = courseService.findAllCourses(session);

    for (Course course : courses) {
      List<Module> modules = course.getModules();
      for (Module module : modules) {
        for(Lesson lesson: module.getLessons()) {
          if (lesson.getId().equals(lessonId)) {
            return lesson.getTopics();
          }
        }
      }
    }
    return new ArrayList<>();
  }

  @GetMapping("/api/topic/{tid}")
  public Topic findTopicById(@PathVariable("tid") Integer topicId, HttpSession session) {

    List<Course> courses = courseService.findAllCourses(session);
    if (courses.size() != 0) {
      for (Course course : courses) {
        List<Module> modules = course.getModules();
        for (Module module : modules) {
          for (Lesson lesson : module.getLessons()) {
            for(Topic topic : lesson.getTopics()){
              if (topic.getId().equals(topicId)) {
                return topic;
              }
            }
          }
        }
      }
    }
    return new Topic();
  }

  @PutMapping("/api/topic/{tid}")
  public Topic updateTopic(@PathVariable("tid") Integer topicId, @RequestBody Topic updatedTopic,
                             HttpSession session) {

    List<Course> courses = courseService.findAllCourses(session);
    if (courses.size() != 0) {
      for (Course course : courses) {
        List<Module> modules = course.getModules();
        for (Module module : modules) {
          List<Lesson> lessons = module.getLessons();
          for(Lesson lesson:lessons) {
            List<Topic> topics = lesson.getTopics();
            for (int i = 0; i < topics.size(); i++) {
              if (topics.get(i).getId().equals(updatedTopic.getId())) {
                topics.set(i, updatedTopic);
                return topics.get(i);
              }
            }
          }
        }
      }
    }
    return new Topic();
  }

  @DeleteMapping("/api/topic/{tid}")
  public List<Topic> deleteTopic(@PathVariable("tid") Integer topicId, HttpSession session) {
    List<Course> courses = courseService.findAllCourses(session);
    if (courses.size() != 0) {
      for (Course course : courses) {
        List<Module> modules = course.getModules();
        for (Module module : modules) {
          List<Lesson> lessons = module.getLessons();
          for(Lesson lesson:lessons) {
            List<Topic> topics = lesson.getTopics();
            for (int i = 0; i < topics.size(); i++) {
              if (topics.get(i).getId().equals(topicId)) {
                topics.remove(i);
                return topics;
              }
            }
          }
        }
      }
    }
    return new ArrayList<>();
  }
}
