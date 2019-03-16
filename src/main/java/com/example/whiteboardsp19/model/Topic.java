package com.example.whiteboardsp19.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Topic {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String topicName;
  @ManyToOne()
  @JsonIgnore
  private Lesson lesson;

  public Lesson getLesson() {
    return lesson;
  }

  public void setLesson(Lesson lesson) {
    this.lesson = lesson;
  }

  @OneToMany(mappedBy = "topic",cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Widget> widgets;

  public Topic(){

  }

  public Topic(Integer id,String topicName,List<Widget> widgets){
    this.id = id;
    this.topicName = topicName;
    this.widgets = widgets;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTopicName() {
    return topicName;
  }

  public void setTopicName(String topicName) {
    this.topicName = topicName;
  }

  public List<Widget> getWidgets() {
    return widgets;
  }

  public void setWidgets(List<Widget> widgets) {
    this.widgets = widgets;
  }

  public void set(Topic newTopic){
    this.topicName = newTopic.getTopicName();
  }
}
