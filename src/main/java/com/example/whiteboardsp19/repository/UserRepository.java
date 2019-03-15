package com.example.whiteboardsp19.repository;

import com.example.whiteboardsp19.model.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
}
