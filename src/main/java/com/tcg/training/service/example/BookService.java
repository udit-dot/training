package com.tcg.training.service.example;

import com.tcg.training.entity.example.Book;
import java.util.List;

public interface BookService {
  Book createBook(Book book);

  Book getBook(Long id);

  List<Book> getAllBooks();

  Book updateBook(Long id, Book book);

  void deleteBook(Long id);
}