package com.tcg.training.repository.example;

import com.tcg.training.entity.example.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}