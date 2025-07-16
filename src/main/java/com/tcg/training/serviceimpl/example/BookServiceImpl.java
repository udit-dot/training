package com.tcg.training.serviceimpl.example;

import com.tcg.training.entity.example.Book;
import com.tcg.training.repository.example.BookRepository;
import com.tcg.training.service.example.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
  @Autowired
  private BookRepository bookRepository;

  @Override
  public Book createBook(Book book) {
    return bookRepository.save(book);
  }

  @Override
  public Book getBook(Long id) {
    return bookRepository.findById(id).orElse(null);
  }

  @Override
  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  @Override
  public Book updateBook(Long id, Book book) {
    Optional<Book> existing = bookRepository.findById(id);
    if (existing.isPresent()) {
      book.setId(id);
      return bookRepository.save(book);
    }
    return null;
  }

  @Override
  public void deleteBook(Long id) {
    bookRepository.deleteById(id);
  }
}