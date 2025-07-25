package com.tcg.training.service.example;

import java.util.List;

import com.tcg.training.dto.example.AuthorBookCollectionDto;
import com.tcg.training.dto.example.AuthorBookResultSetDto;
import com.tcg.training.dto.example.AuthorDto;
import com.tcg.training.dto.example.AuthorWithBooksDto;
import com.tcg.training.entity.example.Author;
import com.tcg.training.projection.example.AuthorBookView;

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

	List<AuthorBookCollectionDto> getAuthorBookCollectionData();

	List<AuthorBookResultSetDto> getAuthorBookByNameAndPrice(String name, Double price);

	List<AuthorBookResultSetDto> fetchPartialAuthorBookInfo(String authorName);

	List<AuthorBookView> getAuthorBookByProjection();

	List<AuthorBookView> getAuthorBookByNative();

	Author updateAuthorNameById(Long id, String name);

	Author saveAuthorWithDto(AuthorDto dto);
	Author saveAuthorWithBooks(AuthorWithBooksDto dto);
}