package com.tcg.training.repository.example;

import com.tcg.training.entity.example.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}