package com.tcg.training.serviceimpl.example;

import com.tcg.training.entity.example.Course;
import com.tcg.training.repository.example.CourseRepository;
import com.tcg.training.service.example.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
  @Autowired
  private CourseRepository courseRepository;

  @Override
  public Course createCourse(Course course) {
    return courseRepository.save(course);
  }

  @Override
  public Course getCourse(Long id) {
    return courseRepository.findById(id).orElse(null);
  }

  @Override
  public List<Course> getAllCourses() {
    return courseRepository.findAll();
  }

  @Override
  public Course updateCourse(Long id, Course course) {
    Optional<Course> existing = courseRepository.findById(id);
    if (existing.isPresent()) {
      course.setId(id);
      return courseRepository.save(course);
    }
    return null;
  }

  @Override
  public void deleteCourse(Long id) {
    courseRepository.deleteById(id);
  }
}