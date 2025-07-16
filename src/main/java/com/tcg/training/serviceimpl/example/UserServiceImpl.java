package com.tcg.training.serviceimpl.example;

import com.tcg.training.entity.example.User;
import com.tcg.training.repository.example.UserRepository;
import com.tcg.training.service.example.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public User createUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public User getUser(Long id) {
    return userRepository.findById(id).orElse(null);
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public User updateUser(Long id, User user) {
    Optional<User> existing = userRepository.findById(id);
    if (existing.isPresent()) {
      user.setId(id);
      return userRepository.save(user);
    }
    return null;
  }

  @Override
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }
}