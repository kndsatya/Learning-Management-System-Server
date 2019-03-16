package com.example.whiteboardsp19.services;

import com.example.whiteboardsp19.model.Course;
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
import java.util.Optional;

import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class CourseService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private CourseRepository courseRepository;

  public CourseService() {

  }


  @PostMapping("/api/courses")
  public List<Course> createCourse(@RequestBody Course course, HttpSession session) {

    if (session.getAttribute("CurrentUser") != null) {
      course.setAuthor((User) session.getAttribute("CurrentUser"));
      course.setModules(new ArrayList<>());
      courseRepository.save(course);
    }
    return findAllCourses(session);
  }

  @GetMapping("/api/courses")
  public List<Course> findAllCourses(HttpSession session) {

     User currentUser = (User) session.getAttribute("CurrentUser");
     if(currentUser==null){
       return new ArrayList<>();
     }
     Optional<User> optionalObject =  userRepository.findById(currentUser.getId());

    if(!optionalObject.isPresent()){
      return new ArrayList<>();
    }

    return optionalObject.get().getAuthoredCourses();
  }

  @GetMapping("/api/courses/{cid}")
  public Course findCourseById(@PathVariable("cid") Integer courseId, HttpSession session) {

    User currentUser = (User) session.getAttribute("CurrentUser");

    if(currentUser==null){
      return new Course();
    }

    Iterable<Course> courseIterable = courseRepository.findAll();
    while (courseIterable.iterator().hasNext()) {
      Course course = courseIterable.iterator().next();
      if (course.getAuthor().getId() == currentUser.getId()) {
        return course;
      }
    }
    return new Course();
  }

  @PutMapping("/api/courses/{cid}")
  public Course updateCourse(@PathVariable("cid") Integer courseId, @RequestBody Course updatedCourse,
                             HttpSession session) {

    User currentUser = (User) session.getAttribute("CurrentUser");

    if(currentUser==null){
      return new Course();
    }

    Iterable<Course> courseIterable = courseRepository.findAll();
    while (courseIterable.iterator().hasNext()) {
      Course existingCourse = courseIterable.iterator().next();
      if (existingCourse.getAuthor().getId() == currentUser.getId()
              && existingCourse.getId() == courseId) {

        existingCourse.set(updatedCourse);
        updatedCourse = courseRepository.save(existingCourse);
        return updatedCourse;
      }
    }

    return new Course();
  }

  @DeleteMapping("/api/courses/{cid}")
  public List<Course> deleteCourse(@PathVariable("cid") Integer courseId, HttpSession session) {

    List<Course> courses = findAllCourses(session);

    for(Course course:courses){
      if(course.getId().equals(courseId)){
        courseRepository.deleteById(courseId);
        courses.remove(course);
        return courses;
      }
    }
    return courses;
  }
}
