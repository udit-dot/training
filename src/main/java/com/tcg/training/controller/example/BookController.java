package com.tcg.training.controller.example;

import com.tcg.training.entity.example.Book;
import com.tcg.training.service.example.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

  @Operation(summary = "Create a new book", description = "Creates a new book with an author")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Book created successfully", content = @Content(schema = @Schema(implementation = Book.class))) })
  @PostMapping
  public ResponseEntity<Book> createBook(@RequestBody Book book) {
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
  public ResponseEntity<Book> getBookById(@Parameter(description = "Book ID", required = true) @PathVariable Long id) {
    Book book = bookService.getBook(id);
    if (book != null) {
      return ResponseEntity.ok(book);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @Operation(summary = "Update book", description = "Updates an existing book and its author")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Book updated", content = @Content(schema = @Schema(implementation = Book.class))),
      @ApiResponse(responseCode = "404", description = "Book not found") })
  @PutMapping("/{id}")
  public ResponseEntity<Book> updateBook(@Parameter(description = "Book ID", required = true) @PathVariable Long id,
      @RequestBody Book book) {
    Book updated = bookService.updateBook(id, book);
    if (updated != null) {
      return ResponseEntity.ok(updated);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @Operation(summary = "Delete book", description = "Deletes a book")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Book deleted") })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBook(@Parameter(description = "Book ID", required = true) @PathVariable Long id) {
    bookService.deleteBook(id);
    return ResponseEntity.noContent().build();
  }
}