package com.example.whiteboardsp19.services;

import com.example.whiteboardsp19.model.User;
import com.example.whiteboardsp19.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public UserService() {

  }

  @PostMapping("/api/register")
  public User register(@RequestBody User user, HttpSession session) {

    Iterator<User> userIterator = userRepository.findAll().iterator();

    while (userIterator.hasNext()) {
      if (userIterator.next().getUsername().equals(user.getUsername())) {
        return new User();
      }
    }

    user.setAuthoredCourses(new ArrayList<>());
    user = userRepository.save(user);
    session.setAttribute("CurrentUser", user);
    return user;
  }


  @GetMapping("/api/profile")
  public User profile(HttpSession session) {
    System.out.println(session.getId());
    if (session.getAttribute("CurrentUser") != null) {
      return (User) session.getAttribute("CurrentUser");
    }
    return new User();
  }

  @PostMapping("/api/login")
  public User login(@RequestBody User user, HttpSession session) {


    Iterator<User> userIterator = userRepository.findAll().iterator();

    while (userIterator.hasNext()) {
      User existingUser = userIterator.next();
      if (existingUser.getUsername().equals(user.getUsername()) &&
              existingUser.getPassword().equals(user.getPassword())) {
        session.setAttribute("CurrentUser",existingUser);
        return existingUser;
      }
    }
    return new User();
  }

  @PutMapping("/api/update")
  public User update(@RequestBody User user, HttpSession session) {

    Optional<User> optionalObject = userRepository.findById(user.getId());
    if (!optionalObject.isPresent()) {
      return new User();
    }
    User existingUser = optionalObject.get();
    existingUser.set(user);
    User updatedUser = userRepository.save(existingUser);
    return updatedUser;
  }

  @GetMapping("/api/logout")
  public void logout(HttpSession session) {
    session.invalidate();
  }

  @GetMapping("/api/users")
  public List<User> findAllUsers() {

    List<User> allUsers = new ArrayList<>();

    Iterator<User> userIterator = userRepository.findAll().iterator();

    while (userIterator.hasNext()) {
      allUsers.add(userIterator.next());
    }
    return allUsers;
  }

  @GetMapping("/api/users/{id}")
  public User findUserById(@PathVariable("id") Integer id) {
    Optional<User> optionalObject = userRepository.findById(id);
    if (!optionalObject.isPresent()) {
      return new User();
    }
    return optionalObject.get();
  }


}
