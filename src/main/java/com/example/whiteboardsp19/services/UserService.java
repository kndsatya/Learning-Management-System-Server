package com.example.whiteboardsp19.services;

import com.example.whiteboardsp19.model.User;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class UserService {

  private List<User> users = new ArrayList<>();

  public UserService(){

//       User alice = new User(123,"alice","alice","alice@husky.neu.edu",
//               "alice","warner",1234567890L,
//               "FACULTY", LocalDate.of(1999,02,01));
//
//       User bob = new User(234,"bob","bob","bob@husky.neu.edu",
//               "bob","warner",1230456789L,
//            "FACULTY",LocalDate.of(1999,02,01));
//
//       users.add(alice);
//       users.add(bob);
  }

  @PostMapping("/api/register")
  public User register(@RequestBody User user, HttpSession session){

    for(User existingUser:users){

      if(existingUser.getUsername().equals(user.getUsername())) {
           return new User();
      }
    }
    Random r = new Random();
    user.setId(r.nextInt(Integer.MAX_VALUE));
    users.add(user);
    session.setAttribute("CurrentUser",user);
    return user;
  }



  @GetMapping("/api/profile")
  public User profile(HttpSession session){
    System.out.println(session.getId());
    if(session.getAttribute("CurrentUser")!=null) {
      return (User) session.getAttribute("CurrentUser");
    }
    return new User();
  }

  @PostMapping("/api/login")
  public User login(@RequestBody User user,HttpSession session){

    for(User existingUser:users){
          if(existingUser.getUsername().equals(user.getUsername()) &&
                  existingUser.getPassword().equals(user.getPassword())){
            session.setAttribute("CurrentUser",existingUser);
            return existingUser;
          }
    }

    return new User();
  }

  @PutMapping("/api/update")
  public User update(@RequestBody User user,HttpSession session){

    for(int i=0;i<users.size();i++){
       if(users.get(i).getId().equals(user.getId())){
          users.set(i,user);
          session.setAttribute("CurrentUser",users.get(i));
          return users.get(i);
       }
    }

    return new User();
  }

  @GetMapping("/api/logout")
  public void logout(HttpSession session){
    session.invalidate();
  }

  @GetMapping("/api/users")
  public List<User> findAllUsers(){
     return users;
  }

  @GetMapping("/api/users/{id}")
  public User findUserById(@PathVariable("id") Integer id){
    for(User user:users){
      if(user.getId().equals(id)){
        return user;
      }
    }
    return new User();
  }


}
