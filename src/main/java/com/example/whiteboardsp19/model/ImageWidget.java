package com.example.whiteboardsp19.model;

public class ImageWidget extends Widget {

  private String src;

  ImageWidget(String src) {
    this.src = src;
  }

  public String getSrc() {
    return src;
  }

  public void setSrc(String src) {
    this.src = src;
  }
}
