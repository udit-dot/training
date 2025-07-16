package com.tcg.training.repository.example;

import com.tcg.training.entity.example.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}