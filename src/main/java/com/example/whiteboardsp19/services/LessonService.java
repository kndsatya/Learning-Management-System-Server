package com.example.whiteboardsp19.services;

import com.example.whiteboardsp19.model.Course;
import com.example.whiteboardsp19.model.Lesson;
import com.example.whiteboardsp19.model.Module;
import com.example.whiteboardsp19.model.User;
import com.example.whiteboardsp19.repository.LessonRepository;

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
public class LessonService {

  @Autowired
  private ModuleService moduleService;
  @Autowired
  private LessonRepository lessonRepository;

  @PostMapping("/api/module/{mid}/lesson")
  public List<Lesson> createLesson(@PathVariable("mid") Integer moduleId, @RequestBody Lesson lesson,
                                   HttpSession session) {


    Module module = moduleService.findModuleById(moduleId, session);
    if (module.getId() != null) {
      lesson.setModule(module);
      lesson.setTopics(new ArrayList<>());
      lessonRepository.save(lesson);
      return findAllLessons(moduleId, session);
    }
    return new ArrayList<>();

  }

  @GetMapping("/api/module/{mid}/lesson")
  public List<Lesson> findAllLessons(@PathVariable("mid") Integer moduleId, HttpSession session) {

    Module module = moduleService.findModuleById(moduleId, session);
    if (module.getId() != null) {
      return module.getLessons();
    }
    return new ArrayList<>();
  }

  @GetMapping("/api/lesson/{lid}")
  public Lesson findLessonById(@PathVariable("lid") Integer lessonId, HttpSession session) {

    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new Lesson();
    }

    Optional<Lesson> optionalObject = lessonRepository.findById(lessonId);

    if (optionalObject.isPresent()) {
      Lesson lesson = optionalObject.get();
      if (lesson.getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        return lesson;
      }
    }

    return new Lesson();
  }

  @PutMapping("/api/lesson/{lid}")
  public Lesson updateLesson(@PathVariable("lid") Integer lessonId, @RequestBody Lesson updatedLesson,
                             HttpSession session) {

    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new Lesson();
    }

    Optional<Lesson> optionalLesson = lessonRepository.findById(lessonId);
    if (optionalLesson.isPresent()) {
      Lesson lesson = optionalLesson.get();
      if (lesson.getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        lesson.set(updatedLesson);
        Lesson resultLesson = lessonRepository.save(lesson);
        return resultLesson;
      }
    }
    return new Lesson();
  }

  @DeleteMapping("/api/lesson/{lid}")
  public List<Lesson> deleteLesson(@PathVariable("lid") Integer lessonId, HttpSession session) {

    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new ArrayList<>();
    }

    Optional<Lesson> optionalLesson = lessonRepository.findById(lessonId);
    if (optionalLesson.isPresent()) {
      Lesson lesson = optionalLesson.get();
      if (lesson.getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        lessonRepository.deleteById(lessonId);
        return findAllLessons(lesson.getModule().getId(), session);
      }
    }
    return new ArrayList<>();
  }
}
