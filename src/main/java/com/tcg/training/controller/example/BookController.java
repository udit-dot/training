package com.tcg.training.controller.example;

import com.tcg.training.dto.example.BookDto;
import com.tcg.training.entity.example.Author;
import com.tcg.training.entity.example.Book;
import com.tcg.training.service.example.AuthorService;
import com.tcg.training.service.example.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/example/books")
@Tag(name = "Book Example Controller", description = "Demonstrates Many-to-One relationship between Book and Author")
public class BookController {
	@Autowired
	private BookService bookService;
	@Autowired
	private AuthorService authorService;

	@Operation(summary = "Create a new book", description = "Creates a new book with an author")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Book created successfully", content = @Content(schema = @Schema(implementation = Book.class))) })
	@PostMapping
	public ResponseEntity<Book> createBook(@RequestBody Book book) {
		Book created = bookService.createBook(book);
		return ResponseEntity.status(201).body(created);
	}

	@Operation(summary = "Create a new book from DTO", description = "Creates a new book from a DTO, sets the author by ID, and saves it.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Book created successfully", content = @Content(schema = @Schema(implementation = Book.class))) })
	@PostMapping("/dto")
	public ResponseEntity<Book> createBookFromDto(@RequestBody BookDto bookDto) {
		ModelMapper modelMapper = new ModelMapper();
		Book book = modelMapper.map(bookDto, Book.class);
		// Set the author from authorId
		Author author = authorService.getAuthor(bookDto.getAuthorId());
		book.setAuthor(author);
		Book created = bookService.createBook(book);
		return ResponseEntity.status(201).body(created);
	}

	@Operation(summary = "Get all books", description = "Retrieves all books with their authors")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Books retrieved", content = @Content(schema = @Schema(implementation = Book.class))) })
	@GetMapping
	public ResponseEntity<List<Book>> getAllBooks() {
		return ResponseEntity.ok(bookService.getAllBooks());
	}

	@Operation(summary = "Get book by ID", description = "Retrieves a book and its author by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Book found", content = @Content(schema = @Schema(implementation = Book.class))),
			@ApiResponse(responseCode = "404", description = "Book not found") })
	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(
			@Parameter(description = "Book ID", required = true) @PathVariable Long id) {
		Book book = bookService.getBook(id);
		return ResponseEntity.ok(book);
	}

	@Operation(summary = "Update book", description = "Updates an existing book and its author")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Book updated", content = @Content(schema = @Schema(implementation = Book.class))),
			@ApiResponse(responseCode = "404", description = "Book not found") })
	@PutMapping("/{id}")
	public ResponseEntity<Book> updateBook(@Parameter(description = "Book ID", required = true) @PathVariable Long id,
			@RequestBody Book book) {
		Book updated = bookService.updateBook(id, book);
		return ResponseEntity.ok(updated);
	}

	@Operation(summary = "Delete book", description = "Deletes a book")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Book deleted") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@Parameter(description = "Book ID", required = true) @PathVariable Long id) {
		bookService.deleteBook(id);
		return ResponseEntity.noContent().build();
	}
}