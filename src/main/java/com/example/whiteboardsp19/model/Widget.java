package com.example.whiteboardsp19.model;

public class Widget {

      private Integer widgetId;
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
