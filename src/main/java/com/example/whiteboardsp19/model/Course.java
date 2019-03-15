package com.example.whiteboardsp19.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String courseName;
  @ManyToOne()
  @JsonIgnore()
  private User author;
  @OneToMany(mappedBy = "course")
  private List<Module> modules;

  public Course(){

  }

  public Course(String courseName,User author){

    this.courseName = courseName;
    this.author = author;

  }

//  public Course(Integer id,String courseName,User author,List<Module> modules){
//
//    this.id = id;
//    this.courseName = courseName;
//    this.author = author;
//    this.modules = modules;
//
//  }

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
