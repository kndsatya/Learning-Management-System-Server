package com.example.whiteboardsp19.model;

import javax.persistence.Entity;

@Entity
public class ImageWidget extends Widget {

  private String src;

  public ImageWidget(String src) {
    this.src = src;
  }

  public ImageWidget(){

  }

  public void unset(){
    this.src = null;
  }

  public void set(ImageWidget newWidget){
    this.src = newWidget.getSrc();
  }

  public String getSrc() {
    return src;
  }

  public void setSrc(String src) {
    this.src = src;
  }
}
