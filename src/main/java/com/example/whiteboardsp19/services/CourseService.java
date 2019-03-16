package com.example.whiteboardsp19.services;

import com.example.whiteboardsp19.model.Course;
import com.example.whiteboardsp19.model.Lesson;
import com.example.whiteboardsp19.model.Module;
import com.example.whiteboardsp19.model.Topic;
import com.example.whiteboardsp19.model.User;
import com.example.whiteboardsp19.repository.CourseRepository;
import com.example.whiteboardsp19.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
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
public class CourseService {

  @Autowired
  private CourseRepository courseRepository;
  private UserService userService;

  public CourseService() {
    this.userService = new UserService();
  }


  @PostMapping("/api/courses")
  public List<Course> createCourse(@RequestBody Course course, HttpSession session) {

    if (session.getAttribute("CurrentUser") != null) {
      course.setAuthor((User) session.getAttribute("CurrentUser"));
      courseRepository.save(course);
    }
    return findAllCourses(session);
  }

  @GetMapping("/api/courses")
  public List<Course> findAllCourses(HttpSession session) {
    List<Course> courses = new ArrayList<>();
    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser != null) {
       User user = userService.findUserById(currentUser.getId());
       System.out.println(user);
       //return user.getAuthoredCourses();
      return courses;
    }

    return courses;
  }

  @GetMapping("/api/courses/{cid}")
  public Course findCourseById(@PathVariable("cid") Integer courseId, HttpSession session) {

    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser != null) {

      User user = userService.findUserById(currentUser.getId());
      List<Course> courses =  user.getAuthoredCourses();

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

      User user = userService.findUserById(currentUser.getId());
      List<Course> courses =  user.getAuthoredCourses();

      for (int i = 0; i < courses.size(); i++) {
        Course course = courses.get(i);
        if (course.getId().equals(courseId) && course.getAuthor().getId().equals(currentUser.getId())) {
          course.set(updatedCourse);
          Course newCourse = courseRepository.save(course);
          return newCourse;
        }
      }
    }
    return new Course();
  }

  @DeleteMapping("/api/courses/{cid}")
  public List<Course> deleteCourse(@PathVariable("cid") Integer courseId, HttpSession session) {

    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser != null) {

      User user = userService.findUserById(currentUser.getId());
      List<Course> courses =  user.getAuthoredCourses();

      for (int i = 0; i < courses.size(); i++) {
        Course course = courses.get(i);
        if (course.getId().equals(courseId)) {
          courseRepository.deleteById(courseId);
          return findAllCourses(session);
        }
      }
    }
    return new ArrayList<>();
  }
}
