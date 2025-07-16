package com.tcg.training.service.example;

import com.tcg.training.entity.example.Student;
import java.util.List;

public interface StudentService {
  Student createStudent(Student student);

  Student getStudent(Long id);

  List<Student> getAllStudents();

  Student updateStudent(Long id, Student student);

  void deleteStudent(Long id);
}