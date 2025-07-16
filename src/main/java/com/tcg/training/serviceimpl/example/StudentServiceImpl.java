package com.tcg.training.serviceimpl.example;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcg.training.entity.example.Course;
import com.tcg.training.entity.example.Student;
import com.tcg.training.repository.example.CourseRepository;
import com.tcg.training.repository.example.StudentRepository;
import com.tcg.training.service.example.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
  @Autowired
  private StudentRepository studentRepository;
  
  @Autowired
  private CourseRepository courseRepository;

  @Override
  public Student createStudent(Student student) {
    return studentRepository.save(student);
  }

  @Override
  public Student getStudent(Long id) {
    return studentRepository.findById(id).orElse(null);
  }

  @Override
  public List<Student> getAllStudents() {
    return studentRepository.findAll();
  }

  @Override
  public Student updateStudent(Long id, Student student) {
    Optional<Student> existing = studentRepository.findById(id);
    existing.get().getCourses().clear();
    
    if (existing.isPresent()) {
      student.setId(id);
      for (Course c : student.getCourses()) {
    	    Course course = courseRepository.findById(c.getId()).orElseThrow();
    	    student.getCourses().add(course);
    	}

      return studentRepository.save(student);
    }
    return null;
  }

  @Override
  public void deleteStudent(Long id) {
    studentRepository.deleteById(id);
  }
}