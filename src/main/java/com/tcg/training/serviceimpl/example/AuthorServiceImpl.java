package com.tcg.training.serviceimpl.example;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcg.training.entity.example.Author;
import com.tcg.training.entity.example.Book;
import com.tcg.training.repository.example.AuthorRepository;
import com.tcg.training.repository.example.BookRepository;
import com.tcg.training.service.example.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {
	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private BookRepository bookRepository;

	@Override
	public Author createAuthor(Author author) {
		return authorRepository.save(author);
	}

	@Override
	public Author getAuthor(Long id) {
		return authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
	}

	@Override
	public List<Author> getAllAuthors() {
		return authorRepository.findAll();
	}

	@Override
	@Transactional
	public Author updateAuthor(Long id, Author author) {
		Author existing = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
//		author.setId(id);
//		return authorRepository.save(author);
		if (author.getName() != null && !author.getName().isEmpty()) {
			existing.setName(author.getName());
		}
		List<Book> managedBooks = new ArrayList<>();
		if (author.getBooks() != null) {
			for (Book b : author.getBooks()) {
				Book managedBook;
				if (b.getId() != null) {
					// Existing book: fetch and update
					managedBook = bookRepository.findById(b.getId())
							.orElseThrow(() -> new RuntimeException("Book not found"));
					managedBook.setTitle(b.getTitle());
					managedBook.setPrice(b.getPrice());
					managedBook.setPublisher(b.getPublisher());

				} else {
					// New book: create and set author
					managedBook = new Book();
					managedBook.setTitle(b.getTitle());
					managedBook.setPrice(b.getPrice());
					managedBook.setPublisher(b.getPublisher());
				}
				managedBook.setAuthor(existing); // Set the managed author
				managedBooks.add(managedBook);
			}
		}
		existing.getBooks().clear();
		existing.setBooks(managedBooks);

		return authorRepository.save(existing);
	}

	@Override
	public void deleteAuthor(Long id) {
		authorRepository.deleteById(id);
	}

	@Override
	public List<Author> getAuthorByName(String name) {
		return authorRepository.findByName(name);
	}

	@Override
	public List<Author> getAuthorsByBookTitle(String title) {
		return authorRepository.findByBooksTitle(title);
	}

	@Override
	public List<Author> getByName(String name) {
		return authorRepository.getAuthorByName(name);
	}

	@Override
	public List<Author> getByBookTitle(String title) {
		return authorRepository.getAuthorByBookTitle(title);
	}

	@Override
	public List<Author> getByBookPriceGreaterThan(Double price) {
		return authorRepository.getAuthorsByPriceGreaterThan(price);
	}

	@Override
	public List<Author> getAuthorsByTitleAndPublisher(String title, String publisher) {
		return authorRepository.getAuthorsByTitleAndPublisher(title, publisher);
	}
}