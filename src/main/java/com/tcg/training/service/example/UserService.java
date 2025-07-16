package com.tcg.training.service.example;

import com.tcg.training.entity.example.User;
import java.util.List;

public interface UserService {
  User createUser(User user);

  User getUser(Long id);

  List<User> getAllUsers();

  User updateUser(Long id, User user);

  void deleteUser(Long id);
}