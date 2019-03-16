package com.example.whiteboardsp19.services;

import com.example.whiteboardsp19.model.HeadingWidget;
import com.example.whiteboardsp19.model.ImageWidget;
import com.example.whiteboardsp19.model.LinkWidget;
import com.example.whiteboardsp19.model.ListWidget;
import com.example.whiteboardsp19.model.Topic;
import com.example.whiteboardsp19.model.User;
import com.example.whiteboardsp19.model.Widget;
import com.example.whiteboardsp19.repository.HeadingWidgetRepository;
import com.example.whiteboardsp19.repository.ImageWidgetRepository;
import com.example.whiteboardsp19.repository.LinkWidgetRepository;
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

import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class LinkWidgetService {

  @Autowired
  private LinkWidgetRepository linkWidgetRepository;


  @Autowired
  private TopicService topicService;

  @PostMapping("/api/topic/{tid}/link/widget")
  public List<LinkWidget> createWidget(@PathVariable("tid") Integer topicId,
                                        @RequestBody LinkWidget linkWidget, HttpSession session) {
    Topic topic = topicService.findTopicById(topicId, session);
    if (topic.getId() != null) {
      if(!linkWidget.getWidgetType().equals("LINK")){
        return findAllWidgets(topicId,session);
      }
      linkWidget.setTopic(topic);
      linkWidgetRepository.save(linkWidget);
      return findAllWidgets(topicId, session);
    }
    return new ArrayList<>();
  }


  @GetMapping("/api/topic/{tid}/link/widget")
  public List<LinkWidget> findAllWidgets(@PathVariable("tid") Integer topicId, HttpSession session) {

    Topic topic = topicService.findTopicById(topicId, session);
    if (topic.getId() != null) {
      List<LinkWidget> result = linkWidgetRepository.findAllLinkWidgets(topic);
      Collections.sort(result,new widgetComparator());
      return result;
    }
    return new ArrayList<>();
  }

  @GetMapping("/api/link/widget/{wid}")
  public LinkWidget findWidgetById(@PathVariable("wid") Integer widgetId, HttpSession session) {


    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new LinkWidget();
    }

    Optional<LinkWidget> optionalObject = linkWidgetRepository.findById(widgetId);

    if (optionalObject.isPresent()) {
      LinkWidget widget = optionalObject.get();
      if (widget.getTopic().getLesson().getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        return widget;
      }
    }
    return new LinkWidget();
  }

  @PutMapping("/api/link/widget/{wid}")
  public LinkWidget updateWidget(@PathVariable("wid") Integer widgetId, @RequestBody LinkWidget updatedWidget,
                                  HttpSession session) {
    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new LinkWidget();
    }

    Optional<LinkWidget> optionalWidget = linkWidgetRepository.findById(widgetId);
    if (optionalWidget.isPresent()) {
      LinkWidget widget = optionalWidget.get();
      if (widget.getTopic().getLesson().getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        widget.set(updatedWidget);
        return linkWidgetRepository.save(widget);
      }
    }
    return new LinkWidget();
  }


  @DeleteMapping("/api/link/widget/{wid}")
  public List<LinkWidget> deleteWidget(@PathVariable("wid") Integer widgetId, HttpSession session) {

    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new ArrayList<>();
    }

    Optional<LinkWidget> optionalWidget = linkWidgetRepository.findById(widgetId);
    if (optionalWidget.isPresent()) {
      LinkWidget widget = optionalWidget.get();
      if (widget.getTopic().getLesson().getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        linkWidgetRepository.deleteById(widgetId);
        return linkWidgetRepository.findAllLinkWidgets(widget.getTopic());
      }
    }
    return new ArrayList<>();
  }

}
