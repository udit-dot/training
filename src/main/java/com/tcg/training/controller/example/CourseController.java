package com.tcg.training.controller.example;

import com.tcg.training.entity.example.Course;
import com.tcg.training.service.example.CourseService;
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
@RequestMapping("/example/courses")
@Tag(name = "Course Example Controller", description = "Demonstrates Many-to-Many relationship between Course and Student")
public class CourseController {
  @Autowired
  private CourseService courseService;

  @Operation(summary = "Create a new course", description = "Creates a new course with students")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Course created successfully", content = @Content(schema = @Schema(implementation = Course.class))) })
  @PostMapping
  public ResponseEntity<Course> createCourse(@RequestBody Course course) {
    Course created = courseService.createCourse(course);
    return ResponseEntity.status(201).body(created);
  }

  @Operation(summary = "Get all courses", description = "Retrieves all courses with their students")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Courses retrieved", content = @Content(schema = @Schema(implementation = Course.class))) })
  @GetMapping
  public ResponseEntity<List<Course>> getAllCourses() {
    return ResponseEntity.ok(courseService.getAllCourses());
  }

  @Operation(summary = "Get course by ID", description = "Retrieves a course and its students by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Course found", content = @Content(schema = @Schema(implementation = Course.class))),
      @ApiResponse(responseCode = "404", description = "Course not found") })
  @GetMapping("/{id}")
  public ResponseEntity<Course> getCourseById(
      @Parameter(description = "Course ID", required = true) @PathVariable Long id) {
    Course course = courseService.getCourse(id);
    if (course != null) {
      return ResponseEntity.ok(course);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @Operation(summary = "Update course", description = "Updates an existing course and its students")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Course updated", content = @Content(schema = @Schema(implementation = Course.class))),
      @ApiResponse(responseCode = "404", description = "Course not found") })
  @PutMapping("/{id}")
  public ResponseEntity<Course> updateCourse(
      @Parameter(description = "Course ID", required = true) @PathVariable Long id, @RequestBody Course course) {
    Course updated = courseService.updateCourse(id, course);
    if (updated != null) {
      return ResponseEntity.ok(updated);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @Operation(summary = "Delete course", description = "Deletes a course and its student enrollments")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Course deleted") })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCourse(
      @Parameter(description = "Course ID", required = true) @PathVariable Long id) {
    courseService.deleteCourse(id);
    return ResponseEntity.noContent().build();
  }
}