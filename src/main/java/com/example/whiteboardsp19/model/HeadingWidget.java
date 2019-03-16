package com.example.whiteboardsp19.model;

import javax.persistence.Entity;

@Entity
public class HeadingWidget extends Widget {

  private Integer size;
  private String text;

  public HeadingWidget(){

  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }


  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void set(HeadingWidget newWidget){
    this.text = newWidget.getText();
    this.size = newWidget.getSize();
  }

}
