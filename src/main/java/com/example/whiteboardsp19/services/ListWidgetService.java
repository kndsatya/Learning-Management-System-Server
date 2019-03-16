package com.example.whiteboardsp19.services;

import com.example.whiteboardsp19.model.LinkWidget;
import com.example.whiteboardsp19.model.ListWidget;
import com.example.whiteboardsp19.model.Topic;
import com.example.whiteboardsp19.model.User;
import com.example.whiteboardsp19.model.Widget;
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
public class ListWidgetService {

  @Autowired
  private ListWidgetRepository listWidgetRepository;

  @Autowired
  private TopicService topicService;

  @PostMapping("/api/topic/{tid}/list/widget")
  public List<ListWidget> createWidget(@PathVariable("tid") Integer topicId,
                                       @RequestBody ListWidget listWidget, HttpSession session) {
    Topic topic = topicService.findTopicById(topicId, session);
    if (topic.getId() != null) {
      if(!listWidget.getWidgetType().equals("LIST")){
        return findAllWidgets(topicId,session);
      }
      listWidget.setTopic(topic);
      listWidgetRepository.save(listWidget);
      return findAllWidgets(topicId, session);
    }
    return new ArrayList<>();
  }


  @GetMapping("/api/topic/{tid}/list/widget")
  public List<ListWidget> findAllWidgets(@PathVariable("tid") Integer topicId, HttpSession session) {

    Topic topic = topicService.findTopicById(topicId, session);
    if (topic.getId() != null) {
      List<ListWidget> result = listWidgetRepository.findAllListWidgets(topic);
      Collections.sort(result,new widgetComparator());
      return result;
    }
    return new ArrayList<>();
  }

  @GetMapping("/api/list/widget/{wid}")
  public ListWidget findWidgetById(@PathVariable("wid") Integer widgetId, HttpSession session) {


    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new ListWidget();
    }

    Optional<ListWidget> optionalObject = listWidgetRepository.findById(widgetId);

    if (optionalObject.isPresent()) {
      ListWidget widget = optionalObject.get();
      if (widget.getTopic().getLesson().getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        return widget;
      }
    }
    return new ListWidget();
  }

  @PutMapping("/api/list/widget/{wid}")
  public ListWidget updateWidget(@PathVariable("wid") Integer widgetId, @RequestBody ListWidget updatedWidget,
                             HttpSession session) {
    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new ListWidget();
    }

    Optional<ListWidget> optionalWidget = listWidgetRepository.findById(widgetId);
    if (optionalWidget.isPresent()) {
      ListWidget widget = optionalWidget.get();
      if (widget.getTopic().getLesson().getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        widget.set(updatedWidget);
        return listWidgetRepository.save(widget);
      }
    }
    return new ListWidget();
  }


  @DeleteMapping("/api/list/widget/{wid}")
  public List<ListWidget> deleteWidget(@PathVariable("wid") Integer widgetId, HttpSession session) {

    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new ArrayList<>();
    }

    Optional<ListWidget> optionalWidget = listWidgetRepository.findById(widgetId);
    if (optionalWidget.isPresent()) {
      ListWidget widget = optionalWidget.get();
      if (widget.getTopic().getLesson().getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        listWidgetRepository.deleteById(widgetId);
        return listWidgetRepository.findAllListWidgets(widget.getTopic());
      }
    }
    return new ArrayList<>();
  }

}
