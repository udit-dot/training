package com.tcg.training.controller.example;

import com.tcg.training.entity.example.User;
import com.tcg.training.service.example.UserService;
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
@RequestMapping("/example/users")
@Tag(name = "User Example Controller", description = "Demonstrates One-to-One relationship between User and Profile")
public class UserController {
  @Autowired
  private UserService userService;

  @Operation(summary = "Create a new user", description = "Creates a new user with a profile")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "User created successfully", content = @Content(schema = @Schema(implementation = User.class))) })
  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody User user) {
    User created = userService.createUser(user);
    return ResponseEntity.status(201).body(created);
  }

  @Operation(summary = "Get all users", description = "Retrieves all users with their profiles")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Users retrieved", content = @Content(schema = @Schema(implementation = User.class))) })
  @GetMapping
  public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @Operation(summary = "Get user by ID", description = "Retrieves a user and their profile by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = User.class))),
      @ApiResponse(responseCode = "404", description = "User not found") })
  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@Parameter(description = "User ID", required = true) @PathVariable Long id) {
    User user = userService.getUser(id);
    if (user != null) {
      return ResponseEntity.ok(user);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @Operation(summary = "Update user", description = "Updates an existing user and their profile")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User updated", content = @Content(schema = @Schema(implementation = User.class))),
      @ApiResponse(responseCode = "404", description = "User not found") })
  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(@Parameter(description = "User ID", required = true) @PathVariable Long id,
      @RequestBody User user) {
    User updated = userService.updateUser(id, user);
    if (updated != null) {
      return ResponseEntity.ok(updated);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @Operation(summary = "Delete user", description = "Deletes a user and their profile")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "User deleted") })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@Parameter(description = "User ID", required = true) @PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }
}