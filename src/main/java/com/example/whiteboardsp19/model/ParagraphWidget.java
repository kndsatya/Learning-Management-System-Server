package com.example.whiteboardsp19.model;

import javax.persistence.Entity;

@Entity
public class ParagraphWidget extends Widget {

  private String text;

  public ParagraphWidget(String text) {
    this.text = text;
  }
  public ParagraphWidget(){

  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void unset(){
    this.text = null;
  }

  public void set(ParagraphWidget newWidget){
    this.text = newWidget.getText();
  }

}
