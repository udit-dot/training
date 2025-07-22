package com.tcg.training.controller.example;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcg.training.entity.example.Author;
import com.tcg.training.service.example.AuthorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/example/authors")
@Tag(name = "Author Example Controller", description = "Demonstrates One-to-Many relationship between Author and Book")
public class AuthorController {
	@Autowired
	private AuthorService authorService;

	@Operation(summary = "Create a new author", description = "Creates a new author with books")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Author created successfully", content = @Content(schema = @Schema(implementation = Author.class))) })
	@PostMapping
	public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
		Author created = authorService.createAuthor(author);
		return ResponseEntity.status(201).body(created);
	}

	@Operation(summary = "Get all authors", description = "Retrieves all authors with their books")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Authors retrieved", content = @Content(schema = @Schema(implementation = Author.class))) })
	@GetMapping
	public ResponseEntity<List<Author>> getAllAuthors() {
		return ResponseEntity.ok(authorService.getAllAuthors());
	}

	@Operation(summary = "Get author by ID", description = "Retrieves an author and their books by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Author found", content = @Content(schema = @Schema(implementation = Author.class))),
			@ApiResponse(responseCode = "404", description = "Author not found") })
	@GetMapping("/{id}")
	public ResponseEntity<Author> getAuthorById(
			@Parameter(description = "Author ID", required = true) @PathVariable Long id) {
		Author author = authorService.getAuthor(id);
		if (author != null) {
			return ResponseEntity.ok(author);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Update author", description = "Updates an existing author and their books")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Author updated", content = @Content(schema = @Schema(implementation = Author.class))),
			@ApiResponse(responseCode = "404", description = "Author not found") })
	@PutMapping("/{id}")
	public ResponseEntity<Author> updateAuthor(
			@Parameter(description = "Author ID", required = true) @PathVariable Long id, @RequestBody Author author) {
		Author updated = authorService.updateAuthor(id, author);

		return ResponseEntity.ok(updated);
	}

	@Operation(summary = "Delete author", description = "Deletes an author and their books")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Author deleted") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAuthor(
			@Parameter(description = "Author ID", required = true) @PathVariable Long id) {
		authorService.deleteAuthor(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Get authors by name", description = "Retrieves all authors for a given name")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "authors found", content = @Content(schema = @Schema(implementation = Author.class))) })
	@GetMapping("/byName/{name}")
	public ResponseEntity<List<Author>> getAuthorByName(
			@Parameter(description = "name to filter authors", required = true) @PathVariable String name) {
		List<Author> authors = authorService.getAuthorByName(name);
		return ResponseEntity.ok(authors);
	}
	
	@Operation(summary = "Get authors by Book", description = "Retrieves all authors for a given book title")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "authors found", content = @Content(schema = @Schema(implementation = Author.class))) })
	@GetMapping("/bytitle/{title}")
	public ResponseEntity<List<Author>> getAuthorsByBookTitle(
			@Parameter(description = "title to filter authors", required = true) @PathVariable String title) {
		List<Author> authors = authorService.getAuthorsByBookTitle(title);
		return ResponseEntity.ok(authors);
	}
	
	@Operation(summary = "Get authors by name", description = "Retrieves all authors for a given name")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "authors found", content = @Content(schema = @Schema(implementation = Author.class))) })
	@GetMapping("/byJpqlName/{name}")
	public ResponseEntity<List<Author>> getByName(
			@Parameter(description = "name to filter authors", required = true) @PathVariable String name) {
		List<Author> authors = authorService.getByName(name);
		return ResponseEntity.ok(authors);
	}
	
	@Operation(summary = "Get authors by Book", description = "Retrieves all authors for a given book title")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "authors found", content = @Content(schema = @Schema(implementation = Author.class))) })
	@GetMapping("/byJpqlTitle/{title}")
	public ResponseEntity<List<Author>> getByBookTitle(
			@Parameter(description = "title to filter authors", required = true) @PathVariable String title) {
		List<Author> authors = authorService.getByBookTitle(title);
		return ResponseEntity.ok(authors);
	}
	
	@Operation(summary = "Get authors by Book price check", description = "Retrieves all authors for a given book price")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "authors found", content = @Content(schema = @Schema(implementation = Author.class))) })
	@GetMapping("/byPriceCheck/{price}")
	public ResponseEntity<List<Author>> getByBookPriceGreaterThan(
			@Parameter(description = "price to filter authors", required = true) @PathVariable Double price) {
		List<Author> authors = authorService.getByBookPriceGreaterThan(price);
		return ResponseEntity.ok(authors);
	}
}