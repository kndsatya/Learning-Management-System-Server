package com.example.whiteboardsp19.model;

import javax.persistence.Entity;

@Entity
public class ListWidget extends Widget{

      private String[] items;
      private Boolean ordered;

      public ListWidget(String[] items,Boolean ordered){
         this.items = items;
         this.ordered = ordered;
      }

      public ListWidget(){

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

  public void unset(){
        this.items=null;
        this.ordered=null;
  }

  public void set(ListWidget newWidget){
        this.ordered = newWidget.getOrdered();
        this.items = newWidget.getItems();
  }

}
