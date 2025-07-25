package com.tcg.training.serviceimpl.example;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcg.training.dto.example.AuthorBookCollectionDto;
import com.tcg.training.dto.example.AuthorBookResultSetDto;
import com.tcg.training.dto.example.AuthorDto;
import com.tcg.training.dto.example.AuthorWithBooksDto;
import com.tcg.training.dto.example.BookSimpleDto;
import com.tcg.training.entity.example.Author;
import com.tcg.training.entity.example.Book;
import com.tcg.training.projection.example.AuthorBookView;
import com.tcg.training.repository.example.AuthorRepository;
import com.tcg.training.repository.example.BookRepository;
import com.tcg.training.service.example.AuthorService;
import org.modelmapper.ModelMapper;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private ModelMapper mapper;

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
		// author.setId(id);
		// return authorRepository.save(author);
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

	@Override
	public List<AuthorBookCollectionDto> getAuthorBookCollectionData() {
		return authorRepository.fetchAuthorBookCollection();
	}

	@Override
	public List<AuthorBookResultSetDto> getAuthorBookByNameAndPrice(String name, Double price) {
		return authorRepository.fetchAuthorBooksByAuthorAndPrice(name, price);
	}

	@Override
	public List<AuthorBookView> getAuthorBookByProjection() {
		return authorRepository.fetchAuthorBookView();
	}

	@Override
	public List<AuthorBookView> getAuthorBookByNative() {
		return authorRepository.fetchAuthorBookViewByNative();
	}

	@Override
	public List<AuthorBookResultSetDto> fetchPartialAuthorBookInfo(String authorName) {
		return authorRepository.fetchPartialAuthorBookInfo(authorName);
	}

	@Override
	@Transactional
	public Author updateAuthorNameById(Long id, String name) {
		int updated = authorRepository.updateAuthorNameById(id, name);
		if (updated > 0) {
			return authorRepository.findById(id).orElse(null);
		} else {
			return null;
		}
	}

	@Override
	public Author saveAuthorWithBooks(AuthorWithBooksDto dto) {
		Author author = mapper.map(dto, Author.class);
		if (dto.getBooks() != null) {
			List<Book> books = dto.getBooks().stream().map(bookDto -> {
				Book book = mapper.map(bookDto, Book.class);
				book.setAuthor(author);
				return book;
			}).toList();
			author.setBooks(books);
		}
		return authorRepository.save(author);
	}

	@Override
	public Author saveAuthorWithDto(AuthorDto authorDto) {
//		ModelMapper modelMapper = new ModelMapper();
		Author author = mapper.map(authorDto, Author.class);
		// Handle LocalDate conversion for birthDate
//		if (authorDto.getBirthDate() != null) {
//			author.setBirthDate(LocalDate.parse(authorDto.getBirthDate()));
//		}
		return authorRepository.save(author);
	}
	
}