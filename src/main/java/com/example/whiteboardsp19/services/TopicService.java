package com.example.whiteboardsp19.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.example.whiteboardsp19.model.HeadingWidget;
import com.example.whiteboardsp19.model.ImageWidget;
import com.example.whiteboardsp19.model.Lesson;
import com.example.whiteboardsp19.model.LinkWidget;
import com.example.whiteboardsp19.model.ListWidget;
import com.example.whiteboardsp19.model.ParagraphWidget;
import com.example.whiteboardsp19.model.Topic;
import com.example.whiteboardsp19.model.User;
import com.example.whiteboardsp19.model.Widget;
import com.example.whiteboardsp19.repository.HeadingWidgetRepository;
import com.example.whiteboardsp19.repository.ImageWidgetRepository;
import com.example.whiteboardsp19.repository.LinkWidgetRepository;
import com.example.whiteboardsp19.repository.ListWidgetRepository;
import com.example.whiteboardsp19.repository.ParagraphWidgetRepository;
import com.example.whiteboardsp19.repository.TopicRepository;
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
public class TopicService {

  @Autowired
  private LessonService lessonService;
  @Autowired
  private TopicRepository topicRepository;
  @Autowired
  private WidgetRepository widgetRepository;
  @Autowired
  private HeadingWidgetRepository headingWidgetRepository;
  @Autowired
  private ImageWidgetRepository imageWidgetRepository;
  @Autowired
  private ParagraphWidgetRepository paragraphWidgetRepository;
  @Autowired
  private ListWidgetRepository listWidgetRepository;
  @Autowired
  private LinkWidgetRepository linkWidgetRepository;

  @PostMapping("/api/lesson/{lid}/topic")
  public List<Topic> createTopic(@PathVariable("lid") Integer lessonId, @RequestBody Topic topic,
                                 HttpSession session) {

    Lesson lesson = lessonService.findLessonById(lessonId, session);
    if (lesson.getId() != null) {
      topic.setLesson(lesson);
      topic.setWidgets(new ArrayList<>());
      topicRepository.save(topic);
      return findAllTopics(lessonId, session);
    }
    return new ArrayList<>();
  }

  @GetMapping("/api/lesson/{lid}/topic")
  public List<Topic> findAllTopics(@PathVariable("lid") Integer lessonId, HttpSession session) {

    Lesson lesson = lessonService.findLessonById(lessonId, session);
    if (lesson.getId() != null) {
      return lesson.getTopics();
    }
    return new ArrayList<>();
  }

  @GetMapping("/api/topic/{tid}")
  public Topic findTopicById(@PathVariable("tid") Integer topicId, HttpSession session) {


    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new Topic();
    }

    Optional<Topic> optionalObject = topicRepository.findById(topicId);

    if (optionalObject.isPresent()) {
      Topic topic = optionalObject.get();
      if (topic.getLesson().getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        return topic;
      }
    }

    return new Topic();

  }

  @PutMapping("/api/topic/{tid}")
  public Topic updateTopic(@PathVariable("tid") Integer topicId, @RequestBody Topic updatedTopic,
                           HttpSession session) {
    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new Topic();
    }

    Optional<Topic> optionalLesson = topicRepository.findById(topicId);
    if (optionalLesson.isPresent()) {
      Topic topic = optionalLesson.get();
      if (topic.getLesson().getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        topic.set(updatedTopic);
        Topic resultTopic = topicRepository.save(topic);
        return resultTopic;
      }
    }
    return new Topic();
  }

  @DeleteMapping("/api/topic/{tid}")
  public List<Topic> deleteTopic(@PathVariable("tid") Integer topicId, HttpSession session) {

    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new ArrayList<>();
    }

    Optional<Topic> optionalLesson = topicRepository.findById(topicId);
    if (optionalLesson.isPresent()) {
      Topic topic = optionalLesson.get();
      if (topic.getLesson().getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        topicRepository.deleteById(topicId);
        return findAllTopics(topic.getLesson().getId(), session);
      }
    }
    return new ArrayList<>();
  }

  @PostMapping("/api/topic/{tid}/widget")
  public List<Widget> createWidget(@PathVariable("tid") Integer topicId,
                                   @RequestBody String requestJSON, HttpSession session) {
    Gson gson = new Gson();
    Widget widget = gson.fromJson(requestJSON, Widget.class);
    Topic topic = findTopicById(topicId, session);
    if (topic.getId() != null) {

      switch (widget.getWidgetType()) {
        case "HEADING":
          HeadingWidget headingWidget = gson.fromJson(requestJSON, HeadingWidget.class);
          headingWidget.setTopic(topic);
          headingWidgetRepository.save(headingWidget);
          break;
        case "IMAGE":
          ImageWidget imageWidget = gson.fromJson(requestJSON, ImageWidget.class);
          imageWidget.setTopic(topic);
          imageWidgetRepository.save(imageWidget);
          break;
        case "PARAGRAPH":
          ParagraphWidget paragraphWidget = gson.fromJson(requestJSON, ParagraphWidget.class);
          paragraphWidget.setTopic(topic);
          paragraphWidgetRepository.save(paragraphWidget);
          break;

        case "LIST":
          ListWidget listWidget = gson.fromJson(requestJSON, ListWidget.class);
          listWidget.setTopic(topic);
          listWidgetRepository.save(listWidget);
          break;

        case "LINK":
          System.out.println(gson.toJson(widget));
          LinkWidget linkWidget = gson.fromJson(requestJSON, LinkWidget.class);
          linkWidget.setTopic(topic);
          linkWidgetRepository.save(linkWidget);
          break;
      }
      return findAllWidgets(topicId, session);
    }
    return new ArrayList<>();
  }


  @GetMapping("/api/topic/{tid}/widget")
  public List<Widget> findAllWidgets(@PathVariable("tid") Integer topicId, HttpSession session) {

    Topic topic = findTopicById(topicId, session);
    if (topic.getId() != null) {
      List<Widget> result = topic.getWidgets();
      Collections.sort(result,new widgetComparator());
      return result;
    }
    return new ArrayList<>();
  }

  @DeleteMapping("/api/topic/{tid}/widget")
  public List<Widget> deleteAllWidgets(@PathVariable("tid") Integer topicId, HttpSession session) {

    User currentUser = (User) session.getAttribute("CurrentUser");

    if (currentUser == null) {
      return new ArrayList<>();
    }

    Optional<Topic> optionalTopic = topicRepository.findById(topicId);
    if (optionalTopic.isPresent()) {
      Topic topic = optionalTopic.get();
      if (topic.getLesson().getModule().getCourse().getAuthor().getId() == currentUser.getId()) {
        List<Widget> widgets = widgetRepository.findWidgetsByTopicId(topic);
        for(Widget widget:widgets){
          widgetRepository.deleteById(widget.getWidgetId());
        }
        return findAllWidgets(topic.getId(),session);
      }
    }

    return new ArrayList<>();
  }

}

