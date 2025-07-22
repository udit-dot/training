package com.tcg.training.serviceimpl.example;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcg.training.entity.example.Book;
import com.tcg.training.repository.example.BookRepository;
import com.tcg.training.service.example.BookService;

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
		return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
	}

	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public Book updateBook(Long id, Book book) {
		Book existing = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

		if (!book.getTitle().isEmpty()) {
			existing.setTitle(book.getTitle());
		}
		if (book.getPrice() != null) {
			existing.setPrice(book.getPrice());
		}
		if (!book.getPublisher().isEmpty()) {
			existing.setPublisher(book.getPublisher());
		}
		return bookRepository.save(existing);
	}

	@Override
	public void deleteBook(Long id) {
		bookRepository.deleteById(id);
	}
}