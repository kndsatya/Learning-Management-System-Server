package com.example.whiteboardsp19.model;

public class ListWidget extends Widget{

      private String[] items;
      private Boolean ordered;

      ListWidget(String[] items,Boolean ordered){
         this.items = items;
         this.ordered = ordered;
      }

  public String[] getItems() {
    return items;
  }

  public void setItems(String[] items) {
    this.items = items;
  }

  public Boolean getOrdered() {
    return ordered;
  }

  public void setOrdered(Boolean ordered) {
    this.ordered = ordered;
  }
}
