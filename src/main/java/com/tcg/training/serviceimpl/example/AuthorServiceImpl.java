package com.tcg.training.serviceimpl.example;

import com.tcg.training.entity.example.Author;
import com.tcg.training.repository.example.AuthorRepository;
import com.tcg.training.service.example.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
  @Autowired
  private AuthorRepository authorRepository;

  @Override
  public Author createAuthor(Author author) {
    return authorRepository.save(author);
  }

  @Override
  public Author getAuthor(Long id) {
    return authorRepository.findById(id).orElse(null);
  }

  @Override
  public List<Author> getAllAuthors() {
    return authorRepository.findAll();
  }

  @Override
  public Author updateAuthor(Long id, Author author) {
    Optional<Author> existing = authorRepository.findById(id);
    if (existing.isPresent()) {
      author.setId(id);
      return authorRepository.save(author);
    }
    return null;
  }

  @Override
  public void deleteAuthor(Long id) {
    authorRepository.deleteById(id);
  }
}