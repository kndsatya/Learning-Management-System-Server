package com.example.whiteboardsp19.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public class Widget {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      protected Integer widgetId;
      @ManyToOne()
      @JsonIgnore
      protected Topic topic;
      protected String name;
      protected String widgetType;
      protected Integer width;
      protected Integer height;
      protected Integer orderNumber;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public Topic getTopic() {
    return topic;
  }

  public void setTopic(Topic topic) {
    this.topic = topic;
  }

  public Widget(Integer widgetId, String widgetType, Integer width, Integer height){
          this.widgetId = widgetId;
          this.widgetType = widgetType;
          this.width = width;
          this.height = height;
      }

      public Widget(){

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

  public Integer getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(Integer orderNumber) {
    this.orderNumber = orderNumber;
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

  public void set(Widget newWidget){
    this.widgetType = newWidget.getWidgetType();
    this.width = newWidget.getWidth();
    this.height = newWidget.getHeight();
    this.orderNumber = newWidget.getOrderNumber();
  }

}
