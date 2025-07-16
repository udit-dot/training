package com.tcg.training.repository.example;

import com.tcg.training.entity.example.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}