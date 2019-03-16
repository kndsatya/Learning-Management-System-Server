package com.example.whiteboardsp19.services;

import com.example.whiteboardsp19.model.Course;
import com.example.whiteboardsp19.model.Module;
import com.example.whiteboardsp19.model.User;
import com.example.whiteboardsp19.repository.CourseRepository;
import com.example.whiteboardsp19.repository.ModuleRepository;

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
import java.util.Random;

import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class ModuleService {

  @Autowired
  private CourseService courseService;
  @Autowired
  private ModuleRepository moduleRepository;
  @Autowired
  private UserService userService;

  @PostMapping("/api/courses/{cid}/modules")
  public List<Module> createModule(@PathVariable("cid") Integer cid, @RequestBody Module module,
                                   HttpSession session) {

    Course course = courseService.findCourseById(cid, session);
    if (course.getId() != null) {
      module.setCourse(course);
      module.setLessons(new ArrayList<>());
      moduleRepository.save(module);
      return findAllModules(cid, session);
    }
    return new ArrayList<>();
  }

  @GetMapping("/api/courses/{cid}/modules")
  public List<Module> findAllModules(@PathVariable("cid") Integer cid, HttpSession session) {
    Course course = courseService.findCourseById(cid, session);
    if (course.getId() != null) {
      return course.getModules();
    }
    return new ArrayList<>();
  }

  @GetMapping("/api/modules/{mid}")
  public Module findModuleById(@PathVariable("mid") Integer moduleId, HttpSession session) {

    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new Module();
    }

    Optional<Module> optionalObject = moduleRepository.findById(moduleId);
    if (optionalObject.isPresent()) {
      Module module = optionalObject.get();
      if (module.getCourse().getAuthor().getId() == currentUser.getId()) {
        return module;
      }
    }
    return new Module();
  }

  @PutMapping("/api/modules/{mid}")
  public Module updateModule(@PathVariable("mid") Integer moduleId, @RequestBody Module updatedModule,
                             HttpSession session) {

    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new Module();
    }

    Optional<Module> optionalModule = moduleRepository.findById(moduleId);
    if (optionalModule.isPresent()) {
      Module module = optionalModule.get();
      if (module.getCourse().getAuthor().getId() == currentUser.getId()) {
        module.set(updatedModule);
        Module resultModule = moduleRepository.save(module);
        return resultModule;
      }
    }
    return new Module();
  }

  @DeleteMapping("/api/modules/{mid}")
  public List<Module> deleteModule(@PathVariable("mid") Integer moduleId, HttpSession session) {

    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new ArrayList<>();
    }

    Optional<Module> optionalModule = moduleRepository.findById(moduleId);
    if (optionalModule.isPresent()) {
      Module module = optionalModule.get();
      if (module.getCourse().getAuthor().getId() == currentUser.getId()) {
        moduleRepository.deleteById(moduleId);
        return findAllModules(module.getCourse().getId(), session);
      }
    }
    return new ArrayList<>();
  }
}
