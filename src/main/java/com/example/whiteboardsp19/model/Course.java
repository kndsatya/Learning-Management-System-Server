package com.example.whiteboardsp19.model;

import java.util.List;

public class Course {

  private Integer id;
  private String courseName;
  private User author;
  private List<Module> modules;

  public Course(){

  }

  public Course(Integer id,String courseName,User author,List<Module> modules){

    this.id = id;
    this.courseName = courseName;
    this.author = author;
    this.modules = modules;

  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public List<Module> getModules() {
    return modules;
  }

  public void setModules(List<Module> modules) {
    this.modules = modules;
  }
}
