package com.example.whiteboardsp19.services;

import com.example.whiteboardsp19.model.HeadingWidget;
import com.example.whiteboardsp19.model.ImageWidget;
import com.example.whiteboardsp19.model.ListWidget;
import com.example.whiteboardsp19.model.Topic;
import com.example.whiteboardsp19.model.User;
import com.example.whiteboardsp19.model.Widget;
import com.example.whiteboardsp19.repository.HeadingWidgetRepository;
import com.example.whiteboardsp19.repository.ImageWidgetRepository;
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
public class ImageWidgetService {

  @Autowired
  private ImageWidgetRepository imageWidgetRepository;

  @Autowired
  private TopicService topicService;

  @PostMapping("/api/topic/{tid}/image/widget")
  public List<ImageWidget> createWidget(@PathVariable("tid") Integer topicId,
                                          @RequestBody ImageWidget imageWidget, HttpSession session) {
    Topic topic = topicService.findTopicById(topicId, session);
    if (topic.getId() != null) {
      if(!imageWidget.getWidgetType().equals("IMAGE")){
        return findAllWidgets(topicId,session);
      }
      imageWidget.setTopic(topic);
      imageWidgetRepository.save(imageWidget);
      return findAllWidgets(topicId, session);
    }
    return new ArrayList<>();
  }


  @GetMapping("/api/topic/{tid}/image/widget")
  public List<ImageWidget> findAllWidgets(@PathVariable("tid") Integer topicId, HttpSession session) {

    Topic topic = topicService.findTopicById(topicId, session);
    if (topic.getId() != null) {
      List<ImageWidget> result = imageWidgetRepository.findAllImageWidgets(topic);
      Collections.sort(result,new widgetComparator());
      return result;
    }
    return new ArrayList<>();
  }

  @GetMapping("/api/image/widget/{wid}")
  public ImageWidget findWidgetById(@PathVariable("wid") Integer widgetId, HttpSession session) {


    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new ImageWidget();
    }

    Optional<ImageWidget> optionalObject = imageWidgetRepository.findById(widgetId);

    if (optionalObject.isPresent()) {
      ImageWidget widget = optionalObject.get();
      if (widget.getTopic().getLesson().getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        return widget;
      }
    }
    return new ImageWidget();
  }

  @PutMapping("/api/image/widget/{wid}")
  public ImageWidget updateWidget(@PathVariable("wid") Integer widgetId, @RequestBody ImageWidget updatedWidget,
                                    HttpSession session) {
    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new ImageWidget();
    }

    Optional<ImageWidget> optionalWidget = imageWidgetRepository.findById(widgetId);
    if (optionalWidget.isPresent()) {
      ImageWidget widget = optionalWidget.get();
      if (widget.getTopic().getLesson().getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        widget.set(updatedWidget);
        return imageWidgetRepository.save(widget);
      }
    }
    return new ImageWidget();
  }


  @DeleteMapping("/api/image/widget/{wid}")
  public List<ImageWidget> deleteWidget(@PathVariable("wid") Integer widgetId, HttpSession session) {

    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new ArrayList<>();
    }

    Optional<ImageWidget> optionalWidget = imageWidgetRepository.findById(widgetId);
    if (optionalWidget.isPresent()) {
      ImageWidget widget = optionalWidget.get();
      if (widget.getTopic().getLesson().getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        imageWidgetRepository.deleteById(widgetId);
        return imageWidgetRepository.findAllImageWidgets(widget.getTopic());
      }
    }
    return new ArrayList<>();
  }

}
