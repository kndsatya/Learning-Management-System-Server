package com.example.whiteboardsp19.services;

import com.google.gson.Gson;

import com.example.whiteboardsp19.model.Course;
import com.example.whiteboardsp19.model.HeadingWidget;
import com.example.whiteboardsp19.model.ImageWidget;
import com.example.whiteboardsp19.model.Lesson;
import com.example.whiteboardsp19.model.ListWidget;
import com.example.whiteboardsp19.model.Module;
import com.example.whiteboardsp19.model.ParagraphWidget;
import com.example.whiteboardsp19.model.Topic;
import com.example.whiteboardsp19.model.User;
import com.example.whiteboardsp19.model.Widget;
import com.example.whiteboardsp19.repository.WidgetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class WidgetService {

  @Autowired
  private WidgetRepository widgetRepository;
  @Autowired
  private TopicService topicService;

  @GetMapping("/api/widget/{wid}")
  public Widget findWidgetById(@PathVariable("wid") Integer widgetId, HttpSession session) {


    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new Widget();
    }

    Optional<Widget> optionalObject = widgetRepository.findById(widgetId);

    if (optionalObject.isPresent()) {
      Widget widget = optionalObject.get();
      if (widget.getTopic().getLesson().getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        return widget;
      }
    }
    return new Widget();
  }

  @PutMapping("/api/widget/{wid}")
  public Widget updateWidget(@PathVariable("wid") Integer widgetId, @RequestBody String requestJSON,
                           HttpSession session) {
    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new Widget();
    }

    Optional<Widget> optionalWidget = widgetRepository.findById(widgetId);
    if (optionalWidget.isPresent()) {
      Widget widget = optionalWidget.get();
      if (widget.getTopic().getLesson().getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        deleteWidget(widgetId,session);
        List<Widget> resultWidgets = topicService.createWidget(widget.getTopic().getId(),requestJSON,session);
        return resultWidgets.get(resultWidgets.size()-1);
      }
    }
    return new Widget();
  }


  @DeleteMapping("/api/widget/{wid}")
  public List<Widget> deleteWidget(@PathVariable("wid") Integer widgetId, HttpSession session) {

    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new ArrayList<>();
    }

    Optional<Widget> optionalWidget = widgetRepository.findById(widgetId);
    if (optionalWidget.isPresent()) {
      Widget widget = optionalWidget.get();
      if (widget.getTopic().getLesson().getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        widgetRepository.deleteById(widgetId);
        return topicService.findAllWidgets(widget.getTopic().getId(),session);
      }
    }
    return new ArrayList<>();
  }

}
