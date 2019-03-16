package com.example.whiteboardsp19.model;

import javax.persistence.Entity;

@Entity
public class LinkWidget extends Widget {

  private String href;
  private String linkText;

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public String getLinkText() {
    return linkText;
  }

  public void setLinkText(String linkText) {
    this.linkText = linkText;
  }

  public void set(LinkWidget newWidget){
    this.href = newWidget.getHref();
    this.linkText = newWidget.getLinkText();
  }

}
