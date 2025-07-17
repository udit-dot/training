package com.tcg.training.serviceimpl.example;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcg.training.dto.StudentDTO;
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
  public Student createStudent(StudentDTO studentDTO) {
	  Student student = new Student();
	  student.setName(studentDTO.getName());
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
	public Student updateStudent(Long id, StudentDTO studentDTO) {
		Optional<Student> existing = studentRepository.findById(id);

		if (existing.isPresent()) {
			Set<Long> courseIds = studentDTO.getCourseIds();
			List<Course> existingCourse = courseRepository.findAllById(courseIds);
			existing.get().getCourses().addAll(existingCourse);
		}

		return studentRepository.save(existing.get());
	}

  @Override
  public void deleteStudent(Long id) {
    studentRepository.deleteById(id);
  }
}