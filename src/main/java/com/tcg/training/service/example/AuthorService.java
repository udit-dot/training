package com.tcg.training.service.example;

import com.tcg.training.entity.example.Author;
import java.util.List;

public interface AuthorService {
  Author createAuthor(Author author);

  Author getAuthor(Long id);

  List<Author> getAllAuthors();

  Author updateAuthor(Long id, Author author);

  void deleteAuthor(Long id);
  
  List<Author> getAuthorByName(String name);
  List<Author> getByName(String name);
  List<Author> getAuthorsByBookTitle(String title);
  List<Author> getByBookTitle(String title);
  List<Author> getByBookPriceGreaterThan(Double price);
  List<Author> getAuthorsByTitleAndPublisher(String title, String publisher);
}