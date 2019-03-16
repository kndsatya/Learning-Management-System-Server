package com.example.whiteboardsp19.services;

import com.example.whiteboardsp19.model.HeadingWidget;
import com.example.whiteboardsp19.model.ImageWidget;
import com.example.whiteboardsp19.model.ListWidget;
import com.example.whiteboardsp19.model.ParagraphWidget;
import com.example.whiteboardsp19.model.Topic;
import com.example.whiteboardsp19.model.User;
import com.example.whiteboardsp19.model.Widget;
import com.example.whiteboardsp19.repository.HeadingWidgetRepository;
import com.example.whiteboardsp19.repository.ImageWidgetRepository;
import com.example.whiteboardsp19.repository.ListWidgetRepository;
import com.example.whiteboardsp19.repository.ParagraphWidgetRepository;
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
public class ParagraphWidgetService {

  @Autowired
  private ParagraphWidgetRepository paragraphWidgetRepository;


  @Autowired
  private TopicService topicService;

  @PostMapping("/api/topic/{tid}/paragraph/widget")
  public List<ParagraphWidget> createWidget(@PathVariable("tid") Integer topicId,
                                       @RequestBody ParagraphWidget paragraphWidget, HttpSession session) {
    Topic topic = topicService.findTopicById(topicId, session);
    if (topic.getId() != null) {
      if(!paragraphWidget.getWidgetType().equals("PARAGRAPH")){
        return findAllWidgets(topicId,session);
      }
      paragraphWidget.setTopic(topic);
      paragraphWidgetRepository.save(paragraphWidget);
      return findAllWidgets(topicId, session);
    }
    return new ArrayList<>();
  }


  @GetMapping("/api/topic/{tid}/paragraph/widget")
  public List<ParagraphWidget> findAllWidgets(@PathVariable("tid") Integer topicId, HttpSession session) {

    Topic topic = topicService.findTopicById(topicId, session);
    if (topic.getId() != null) {
      List<ParagraphWidget> result = paragraphWidgetRepository.findAllParagraphWidgets(topic);
      Collections.sort(result,new widgetComparator());
      return result;
    }
    return new ArrayList<>();
  }

  @GetMapping("/api/paragraph/widget/{wid}")
  public ParagraphWidget findWidgetById(@PathVariable("wid") Integer widgetId, HttpSession session) {


    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new ParagraphWidget();
    }

    Optional<ParagraphWidget> optionalObject = paragraphWidgetRepository.findById(widgetId);

    if (optionalObject.isPresent()) {
      ParagraphWidget widget = optionalObject.get();
      if (widget.getTopic().getLesson().getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        return widget;
      }
    }
    return new ParagraphWidget();
  }

  @PutMapping("/api/paragraph/widget/{wid}")
  public ParagraphWidget updateWidget(@PathVariable("wid") Integer widgetId, @RequestBody ParagraphWidget updatedWidget,
                                  HttpSession session) {
    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new ParagraphWidget();
    }

    Optional<ParagraphWidget> optionalWidget = paragraphWidgetRepository.findById(widgetId);
    if (optionalWidget.isPresent()) {
      ParagraphWidget widget = optionalWidget.get();
      if (widget.getTopic().getLesson().getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        widget.set(updatedWidget);
        return paragraphWidgetRepository.save(widget);
      }
    }
    return new ParagraphWidget();
  }


  @DeleteMapping("/api/paragraph/widget/{wid}")
  public List<ParagraphWidget> deleteWidget(@PathVariable("wid") Integer widgetId, HttpSession session) {

    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new ArrayList<>();
    }

    Optional<ParagraphWidget> optionalWidget = paragraphWidgetRepository.findById(widgetId);
    if (optionalWidget.isPresent()) {
      ParagraphWidget widget = optionalWidget.get();
      if (widget.getTopic().getLesson().getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        paragraphWidgetRepository.deleteById(widgetId);
        return paragraphWidgetRepository.findAllParagraphWidgets(widget.getTopic());
      }
    }
    return new ArrayList<>();
  }

}
