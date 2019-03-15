package com.example.whiteboardsp19.services;

import com.example.whiteboardsp19.model.Course;
import com.example.whiteboardsp19.model.Lesson;
import com.example.whiteboardsp19.model.Module;
import com.example.whiteboardsp19.model.Topic;
import com.example.whiteboardsp19.model.User;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class CourseService {

  private static List<Course> courses;

  public CourseService() {
//
//    User alice = new User(123, "alice", "alice", "alice@husky.neu.edu",
//            "alice", "warner", 1234567890L,
//            "FACULTY", LocalDate.of(1999, 02, 01));
//
//    User bob = new User(234, "bob", "bob", "bob@husky.neu.edu",
//            "bob", "warner", 1230456789L,
//            "FACULTY", LocalDate.of(1999, 02, 01));
//
//    Topic topic = new Topic(123,"first topic",new ArrayList<>());
//    List<Topic> topicList = new ArrayList<>();
//    topicList.add(topic);
//    Lesson lesson = new Lesson(123,"firstLesson",topicList);
//    List<Lesson> lessonList = new ArrayList<>();
//    lessonList.add(lesson);
//    Module module = new Module(123,"Module Name",lessonList);
//    List<Module> moduleList = new ArrayList<>();
//    moduleList.add(module);
//    Course webDev = new Course(123, "Web Dev", alice, moduleList);
//    Course softwareEngineering = new Course(234, "Software Engineering", alice, new ArrayList<>());

    courses = new ArrayList<>();
//    courses.add(webDev);
//    courses.add(softwareEngineering);
  }


  @PostMapping("/api/courses")
  public List<Course> createCourse(@RequestBody Course course, HttpSession session) {

    if (session.getAttribute("CurrentUser") != null) {

      course.setAuthor((User) session.getAttribute("CurrentUser"));
      Random r = new Random();
      course.setId(r.nextInt(Integer.MAX_VALUE));
      course.setModules(new ArrayList<>());
      courses.add(course);
    }

    return findAllCourses(session);
  }

  @GetMapping("/api/courses")
  public List<Course> findAllCourses(HttpSession session) {
    List<Course> courses = new ArrayList<>();
    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser != null) {
      for (Course course : this.courses) {
        if (course.getAuthor().getId().equals(currentUser.getId())) {
          courses.add(course);
        }
      }
    }

    return courses;
  }

  @GetMapping("/api/courses/{cid}")
  public Course findCourseById(@PathVariable("cid") Integer courseId, HttpSession session) {

    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser != null) {
      for (Course course : courses) {

        if (course.getId().equals(courseId) && course.getAuthor().getId().equals(currentUser.getId())) {

          return course;
        }
      }
    }

    return new Course();
  }

  @PutMapping("/api/courses/{cid}")
  public Course updateCourse(@PathVariable("cid") Integer courseId, @RequestBody Course updatedCourse,
                             HttpSession session) {

    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser != null) {

      for (int i = 0; i < courses.size(); i++) {
        Course course = courses.get(i);
        if (course.getId().equals(courseId) && course.getAuthor().getId().equals(currentUser.getId())) {
          courses.set(i, updatedCourse);
          return courses.get(i);
        }
      }
    }
    return new Course();
  }

  @DeleteMapping("/api/courses/{cid}")
  public List<Course> deleteCourse(@PathVariable("cid") Integer courseId, HttpSession session) {

    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser != null) {

      for (int i = 0; i < courses.size(); i++) {
        Course course = courses.get(i);
        if (course.getId().equals(courseId)) {
          courses.remove(i);
          return findAllCourses(session);
        }
      }
    }
    return new ArrayList<>();
  }
}
