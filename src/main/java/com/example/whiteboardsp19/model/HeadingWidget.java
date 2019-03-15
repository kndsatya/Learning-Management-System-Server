package com.example.whiteboardsp19.model;

public class HeadingWidget extends Widget {

  private Integer size;

  HeadingWidget(Integer size) {
    this.size = size;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }
}
