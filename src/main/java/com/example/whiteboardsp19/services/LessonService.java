package com.example.whiteboardsp19.services;

import com.example.whiteboardsp19.model.Course;
import com.example.whiteboardsp19.model.Lesson;
import com.example.whiteboardsp19.model.Module;

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
public class LessonService {

  private CourseService courseService = new CourseService();

  @PostMapping("/api/module/{mid}/lesson")
  public List<Lesson> createLesson(@PathVariable("mid") Integer moduleId, @RequestBody Lesson lesson,
                                   HttpSession session) {

    List<Course> courses = courseService.findAllCourses(session);
    if (courses.size()!=0) {
      for (Course course : courses) {
        List<Module> modules = course.getModules();
        for (Module module : modules) {
          if (module.getId().equals(moduleId)) {
            List<Lesson> lessons = module.getLessons();
            Random r = new Random();
            lesson.setId(r.nextInt(Integer.MAX_VALUE));
            lesson.setTopics(new ArrayList<>());
            lessons.add(lesson);
            return lessons;
          }
        }
      }
    }
    return new ArrayList<>();

  }

  @GetMapping("/api/module/{mid}/lesson")
  public List<Lesson> findAllLessons(@PathVariable("mid") Integer moduleId, HttpSession session) {

    List<Course> courses = courseService.findAllCourses(session);

    for (Course course : courses) {
      List<Module> modules = course.getModules();
      for (Module module : modules) {
        if (module.getId().equals(moduleId)) {
          return module.getLessons();
        }
      }
    }
    return new ArrayList<>();
  }

  @GetMapping("/api/lesson/{lid}")
  public Lesson findLessonById(@PathVariable("lid") Integer lessonId, HttpSession session) {

    List<Course> courses = courseService.findAllCourses(session);
    if (courses.size() != 0) {
      for (Course course : courses) {
        List<Module> modules = course.getModules();
        for (Module module : modules) {
          for (Lesson lesson : module.getLessons()) {
            if (lesson.getId().equals(lessonId)) {
              return lesson;
            }
          }
        }
      }
    }
    return new Lesson();
  }

  @PutMapping("/api/lesson/{lid}")
  public Lesson updateLesson(@PathVariable("lid") Integer lessonId, @RequestBody Lesson updatedLesson,
                             HttpSession session) {

    List<Course> courses = courseService.findAllCourses(session);
    if (courses.size()!=0) {
      for (Course course : courses) {
        List<Module> modules = course.getModules();
        for (Module module : modules) {
          List<Lesson> lessons = module.getLessons();
          for (int i = 0; i < lessons.size(); i++) {
            if (lessons.get(i).getId().equals(updatedLesson.getId())) {
              lessons.set(i, updatedLesson);
              return lessons.get(i);
            }
          }
        }
      }
    }
    return new Lesson();
  }

  @DeleteMapping("/api/lesson/{lid}")
  public List<Lesson> deleteLesson(@PathVariable("lid") Integer lessonId, HttpSession session) {
    List<Course> courses = courseService.findAllCourses(session);
    if (courses.size() != 0) {
      for (Course course : courses) {
        List<Module> modules = course.getModules();
        for (Module module : modules) {
          List<Lesson> lessons = module.getLessons();
          for (int i = 0; i < lessons.size(); i++) {
            if (lessons.get(i).getId().equals(lessonId)) {
              lessons.remove(i);
              return lessons;
            }
          }
        }
      }
    }
    return new ArrayList<>();
  }
}
