package com.tcg.training.controller.example;

import com.tcg.training.entity.example.Student;
import com.tcg.training.service.example.StudentService;
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
@RequestMapping("/example/students")
@Tag(name = "Student Example Controller", description = "Demonstrates Many-to-Many relationship between Student and Course")
public class StudentController {
  @Autowired
  private StudentService studentService;

  @Operation(summary = "Create a new student", description = "Creates a new student with courses")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Student created successfully", content = @Content(schema = @Schema(implementation = Student.class))) })
  @PostMapping
  public ResponseEntity<Student> createStudent(@RequestBody Student student) {
    Student created = studentService.createStudent(student);
    return ResponseEntity.status(201).body(created);
  }

  @Operation(summary = "Get all students", description = "Retrieves all students with their courses")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Students retrieved", content = @Content(schema = @Schema(implementation = Student.class))) })
  @GetMapping
  public ResponseEntity<List<Student>> getAllStudents() {
    return ResponseEntity.ok(studentService.getAllStudents());
  }

  @Operation(summary = "Get student by ID", description = "Retrieves a student and their courses by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Student found", content = @Content(schema = @Schema(implementation = Student.class))),
      @ApiResponse(responseCode = "404", description = "Student not found") })
  @GetMapping("/{id}")
  public ResponseEntity<Student> getStudentById(
      @Parameter(description = "Student ID", required = true) @PathVariable Long id) {
    Student student = studentService.getStudent(id);
    if (student != null) {
      return ResponseEntity.ok(student);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @Operation(summary = "Update student", description = "Updates an existing student and their courses")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Student updated", content = @Content(schema = @Schema(implementation = Student.class))),
      @ApiResponse(responseCode = "404", description = "Student not found") })
  @PutMapping("/{id}")
  public ResponseEntity<Student> updateStudent(
      @Parameter(description = "Student ID", required = true) @PathVariable Long id, @RequestBody Student student) {
    Student updated = studentService.updateStudent(id, student);
    if (updated != null) {
      return ResponseEntity.ok(updated);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @Operation(summary = "Delete student", description = "Deletes a student and their course enrollments")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Student deleted") })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteStudent(
      @Parameter(description = "Student ID", required = true) @PathVariable Long id) {
    studentService.deleteStudent(id);
    return ResponseEntity.noContent().build();
  }
}