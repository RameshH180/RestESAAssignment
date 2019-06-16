package com.spring.assignment.service;

import com.spring.assignment.model.User;

import java.util.List;

public interface UserService {

    public List<User> getAllUsers(Integer pageNum);

    public void createUser(User user);

    public User getUser(Long id);

    public void deleteUser(Long id);

   public List<User> searchUsers(String text);
}
