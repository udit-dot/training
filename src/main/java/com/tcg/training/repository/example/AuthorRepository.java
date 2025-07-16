package com.tcg.training.repository.example;

import com.tcg.training.entity.example.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}