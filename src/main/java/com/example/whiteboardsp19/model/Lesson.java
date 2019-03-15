package com.example.whiteboardsp19.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Lesson {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String lessonName;
  @ManyToOne()
  private String module;
  @OneToMany(mappedBy = "lesson")
  private List<Topic> topics;

  public Lesson(){

  }

  public Lesson(Integer id,String lessonName,List<Topic> topics){

    this.id = id;
    this.lessonName = lessonName;
    this.topics = topics;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLessonName() {
    return lessonName;
  }

  public void setLessonName(String lessonName) {
    this.lessonName = lessonName;
  }

  public List<Topic> getTopics() {
    return topics;
  }

  public void setTopics(List<Topic> topics) {
    this.topics = topics;
  }
}
