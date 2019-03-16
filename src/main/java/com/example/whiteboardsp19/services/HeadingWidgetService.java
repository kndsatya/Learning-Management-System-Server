package com.example.whiteboardsp19.services;

import com.google.gson.Gson;

import com.example.whiteboardsp19.model.HeadingWidget;
import com.example.whiteboardsp19.model.ImageWidget;
import com.example.whiteboardsp19.model.LinkWidget;
import com.example.whiteboardsp19.model.ListWidget;
import com.example.whiteboardsp19.model.ParagraphWidget;
import com.example.whiteboardsp19.model.Topic;
import com.example.whiteboardsp19.model.User;
import com.example.whiteboardsp19.model.Widget;
import com.example.whiteboardsp19.repository.HeadingWidgetRepository;
import com.example.whiteboardsp19.repository.ListWidgetRepository;
import com.example.whiteboardsp19.repository.WidgetRepository;

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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class HeadingWidgetService {

  @Autowired
  private HeadingWidgetRepository headingWidgetRepository;

  @Autowired
  private TopicService topicService;

  @PostMapping("/api/topic/{tid}/heading/widget")
  public List<HeadingWidget> createWidget(@PathVariable("tid") Integer topicId,
                                   @RequestBody HeadingWidget headingWidget, HttpSession session) {
    Topic topic = topicService.findTopicById(topicId, session);
    if (topic.getId() != null) {
          if(!headingWidget.getWidgetType().equals("HEADING")){
            return findAllWidgets(topicId,session);
          }
          headingWidget.setTopic(topic);
          headingWidgetRepository.save(headingWidget);
         return findAllWidgets(topicId, session);
    }
    return new ArrayList<>();
  }


  @GetMapping("/api/topic/{tid}/heading/widget")
  public List<HeadingWidget> findAllWidgets(@PathVariable("tid") Integer topicId, HttpSession session) {

    Topic topic = topicService.findTopicById(topicId, session);
    if (topic.getId() != null) {
      List<HeadingWidget> result = headingWidgetRepository.findAllHeadingWidgets(topic);
      Collections.sort(result,new widgetComparator());
      return result;
    }
    return new ArrayList<>();
  }

  @GetMapping("/api/heading/widget/{wid}")
  public HeadingWidget findWidgetById(@PathVariable("wid") Integer widgetId, HttpSession session) {


    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new HeadingWidget();
    }

    Optional<HeadingWidget> optionalObject = headingWidgetRepository.findById(widgetId);

    if (optionalObject.isPresent()) {
      HeadingWidget widget = optionalObject.get();
      if (widget.getTopic().getLesson().getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        return widget;
      }
    }
    return new HeadingWidget();
  }

  @PutMapping("/api/heading/widget/{wid}")
  public HeadingWidget updateWidget(@PathVariable("wid") Integer widgetId, @RequestBody HeadingWidget updatedWidget,
                                 HttpSession session) {
    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new HeadingWidget();
    }

    Optional<HeadingWidget> optionalWidget = headingWidgetRepository.findById(widgetId);
    if (optionalWidget.isPresent()) {
      HeadingWidget widget = optionalWidget.get();
      if (widget.getTopic().getLesson().getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        widget.set(updatedWidget);
        return headingWidgetRepository.save(widget);
      }
    }
    return new HeadingWidget();
  }


  @DeleteMapping("/api/heading/widget/{wid}")
  public List<HeadingWidget> deleteWidget(@PathVariable("wid") Integer widgetId, HttpSession session) {

    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new ArrayList<>();
    }

    Optional<HeadingWidget> optionalWidget = headingWidgetRepository.findById(widgetId);
    if (optionalWidget.isPresent()) {
      HeadingWidget widget = optionalWidget.get();
      if (widget.getTopic().getLesson().getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        headingWidgetRepository.deleteById(widgetId);
        return headingWidgetRepository.findAllHeadingWidgets(widget.getTopic());
      }
    }
    return new ArrayList<>();
  }

}
