package com.example.whiteboardsp19.model;

public class ParagraphWidget extends Widget {

  private String text;

  ParagraphWidget(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
