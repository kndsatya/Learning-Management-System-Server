package com.example.whiteboardsp19.model;

import java.util.List;

public class Topic {

  private Integer id;
  private String topicName;
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
}
