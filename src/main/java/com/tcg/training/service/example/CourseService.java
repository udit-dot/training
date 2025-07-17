package com.tcg.training.service.example;

import com.tcg.training.dto.CourseDTO;
import com.tcg.training.entity.example.Course;
import java.util.List;

public interface CourseService {
  Course createCourse(CourseDTO courseDTO);

  Course getCourse(Long id);

  List<Course> getAllCourses();

  Course updateCourse(Long id, CourseDTO courseDTO);

  void deleteCourse(Long id);
}