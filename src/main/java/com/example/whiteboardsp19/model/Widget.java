package com.example.whiteboardsp19.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Widget {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Integer widgetId;
      @ManyToOne()
      @JsonIgnore
      private Topic topic;
      private String widgetType;
      private Integer width;
      private Integer height;

      Widget(Integer widgetId, String widgetType, Integer width, Integer height){
          this.widgetId = widgetId;
          this.widgetType = widgetType;
          this.width = width;
          this.height = height;
      }

      Widget(){

      }

  public Integer getWidgetId() {
    return widgetId;
  }

  public void setWidgetId(Integer widgetId) {
    this.widgetId = widgetId;
  }

  public String getWidgetType() {
    return widgetType;
  }

  public void setWidgetType(String widgetType) {
    this.widgetType = widgetType;
  }

  public Integer getWidth() {
    return width;
  }

  public void setWidth(Integer width) {
    this.width = width;
  }

  public Integer getHeight() {
    return height;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }

}
