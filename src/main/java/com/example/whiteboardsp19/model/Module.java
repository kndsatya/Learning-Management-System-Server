package com.example.whiteboardsp19.model;

import java.util.List;

public class Module {

  private Integer id;
  private String moduleName;
  private List<Lesson> lessons;

  public Module(){

  }

  public Module(Integer id,String moduleName,List<Lesson> lessons){

    this.id = id;
    this.moduleName = moduleName;
    this.lessons = lessons;
  }

  public String getModuleName() {
    return moduleName;
  }

  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }

  public List<Lesson> getLessons() {
    return lessons;
  }

  public void setLessons(List<Lesson> lessons) {
    this.lessons = lessons;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}
