package com.example.whiteboardsp19.services;

import com.example.whiteboardsp19.model.Course;
import com.example.whiteboardsp19.model.Module;

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
public class ModuleService {

  private CourseService courseService = new CourseService();

  @PostMapping("/api/courses/{cid}/modules")
  public List<Module> createModule(@PathVariable("cid") Integer cid, @RequestBody Module module,
                             HttpSession session){

    Course course = courseService.findCourseById(cid,session);

    if(course!=null){
      List<Module> currentModules = course.getModules();
      Random r = new Random();
      module.setId(r.nextInt(Integer.MAX_VALUE));
      module.setLessons(new ArrayList<>());
      currentModules.add(module);
      course.setModules(currentModules);
      return currentModules;
    }

    return null;

  }

  @GetMapping("/api/courses/{cid}/modules")
  public List<Module> findAllModules(@PathVariable("cid") Integer cid,HttpSession session){

    Course course = courseService.findCourseById(cid,session);
    if(course!=null){
      return course.getModules();
    }
    return null;

  }

  @GetMapping("/api/modules/{mid}")
  public Module findModuleById(@PathVariable("mid") Integer moduleId,HttpSession session){

    List<Course> courses = courseService.findAllCourses(session);
    if(courses!=null){
       for(Course course:courses){
         List<Module> modules = course.getModules();
         for(Module module:modules){
           if(module.getId().equals(moduleId)){
             return module;
           }
         }
       }
    }
    return null;
  }

  @PutMapping("/api/modules/{mid}")
  public Module updateModule(@PathVariable("mid") Integer moduleId,@RequestBody Module updatedModule,
                             HttpSession session){

    List<Course> courses = courseService.findAllCourses(session);
    if(courses!=null){
      for(Course course:courses){
        List<Module> modules = course.getModules();
        for(int i=0;i<modules.size();i++){
          if(modules.get(i).getId().equals(moduleId)){
            modules.set(i,updatedModule);
            return modules.get(i);
          }
        }
      }
    }
    return null;
  }

  @DeleteMapping("/api/modules/{mid}")
  public List<Module> deleteModule(@PathVariable("mid") Integer moduleId,HttpSession session){

    List<Course> courses = courseService.findAllCourses(session);
    if(courses!=null){
      for(Course course:courses){
        List<Module> modules = course.getModules();
        for(int i=0;i<modules.size();i++){
          if(modules.get(i).getId().equals(moduleId)){
            modules.remove(i);
            return modules;
          }
        }
      }
    }
    return null;
  }
}
