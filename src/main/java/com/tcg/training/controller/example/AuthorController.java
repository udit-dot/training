package com.tcg.training.controller.example;

import com.tcg.training.entity.example.Author;
import com.tcg.training.service.example.AuthorService;
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
    if (updated != null) {
      return ResponseEntity.ok(updated);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @Operation(summary = "Delete author", description = "Deletes an author and their books")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Author deleted") })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAuthor(
      @Parameter(description = "Author ID", required = true) @PathVariable Long id) {
    authorService.deleteAuthor(id);
    return ResponseEntity.noContent().build();
  }
}