package com.tcg.training.service.example;

import com.tcg.training.dto.StudentDTO;
import com.tcg.training.entity.example.Student;
import java.util.List;

public interface StudentService {
  Student createStudent(StudentDTO studentDTO);

  Student getStudent(Long id);

  List<Student> getAllStudents();

  Student updateStudent(Long id, StudentDTO student);

  void deleteStudent(Long id);
}