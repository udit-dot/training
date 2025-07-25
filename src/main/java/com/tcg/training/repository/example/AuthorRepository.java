package com.tcg.training.repository.example;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcg.training.dto.example.AuthorBookCollectionDto;
import com.tcg.training.dto.example.AuthorBookResultSetDto;
import com.tcg.training.entity.example.Author;
import com.tcg.training.projection.example.AuthorBookView;

public interface AuthorRepository extends JpaRepository<Author, Long> {

	// Method naming conventions
	List<Author> findByName(String name);

	List<Author> findByBooksTitle(String name);

	// JPQL queries
	@Query("Select a from Author a Where a.name = :name")
	List<Author> getAuthorByName(@Param("name") String name);

	@Query("Select a from Author a Join a.books b Where b.title = :title")
	List<Author> getAuthorByBookTitle(@Param("title") String title);

	// Native query
	@Query(value = "Select a.* from Author a Join Book b ON a.id = b.author_id where b.price > :price", nativeQuery = true)
	List<Author> getAuthorsByPriceGreaterThan(@Param("price") Double price);

	@Query(value = "Select a.* from Author a Join Book b ON a.id = b.author_id where b.title =:title"
			+ " And b.publisher =:publisher", nativeQuery = true)
	List<Author> getAuthorsByTitleAndPublisher(@Param("title") String title, @Param("publisher") String publisher);

	// DTO projection using JPQL constructor expression
	@Query("SELECT new com.tcg.training.dto.example.AuthorBookCollectionDto(a.name, b.title, b.price, b.publisher) "
			+ "FROM Author a JOIN a.books b")
	List<AuthorBookCollectionDto> fetchAuthorBookCollection();

	@Query("SELECT new com.tcg.training.dto.example.AuthorBookCollectionDto(a.name, b.title, b.price) "
			+ "FROM Author a JOIN a.books b WHERE b.publisher = :publisher")
	List<AuthorBookCollectionDto> fetchAuthorBooksByPublisher(@Param("publisher") String publisher);

	// DTO projection using JPQL interface
	@Query("SELECT a.name AS authorName, b.title AS bookTitle, b.price AS bookPrice, b.publisher AS bookPublisher "
			+ "FROM Author a JOIN a.books b")
	List<AuthorBookView> fetchAuthorBookView();

	@Query(value = "SELECT a.name AS authorName, b.title AS bookTitle, b.price AS bookPrice, b.publisher AS bookPublisher "
			+ "FROM Author a Join Book b ON a.id = b.author_id", nativeQuery = true)
	List<AuthorBookView> fetchAuthorBookViewByNative();

	// SqlResultSetMapping
	@Query(value = "SELECT a.name as authorName, b.title as bookTitle, b.publisher, b.price "
			+ "FROM Author a JOIN Book b ON a.id = b.author_id WHERE a.name = :name and b.price > :price", nativeQuery = true)
	List<AuthorBookResultSetDto> fetchAuthorBooksByAuthorAndPrice(@Param("name") String name,
			@Param("price") Double price);

	@Query(value = "SELECT a.name as authorName, b.title as bookTitle, b.publisher "
			+ "FROM Author a JOIN Book b ON a.id = b.author_id WHERE a.name = :name", nativeQuery = true)
	List<AuthorBookResultSetDto> fetchPartialAuthorBookInfo(@Param("name") String authorName);

	@Modifying
	@Query("UPDATE Author a SET a.name = :name WHERE a.id = :id")
	int updateAuthorNameById(@Param("id") Long id, @Param("name") String name);

}