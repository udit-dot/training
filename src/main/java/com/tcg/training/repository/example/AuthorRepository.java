package com.tcg.training.repository.example;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcg.training.entity.example.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

	List<Author> findByName(String name);
	
	List<Author> findByBooksTitle(String name);
	
	@Query("Select a from Author a Where a.name = :name")
	List<Author> getAuthorByName(@Param("name") String name);
	
	@Query("Select a from Author a Join a.books b Where b.title = :title")
	List<Author> getAuthorByBookTitle(@Param("title") String title);
	
	@Query(value = "Select a.* from Author a Join Book b ON a.id = b.author_id where b.price > :price"
			, nativeQuery = true)
	List<Author> getAuthorsByPriceGreaterThan(@Param("price") Double price);
	
	@Query(value = "Select a.* from Author a Join Book b ON a.id = b.author_id where b.title =:title"
			+ " And b.publisher =:publisher"
			, nativeQuery = true)
	List<Author> getAuthorsByTitleAndPublisher(@Param("title") String title, @Param("publisher") String publisher);
}