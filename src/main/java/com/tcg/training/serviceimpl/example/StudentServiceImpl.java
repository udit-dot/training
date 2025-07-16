package com.tcg.training.serviceimpl.example;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		Student existing = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));

		// Update simple fields
		existing.setName(student.getName());

		// Update courses 
		Set<Course> newCourses = new HashSet<>();
		if (student.getCourses() != null) {
			for (Course c : student.getCourses()) {
				Course managedCourse = courseRepository.findById(c.getId())
						.orElseThrow(() -> new RuntimeException("Course not found"));
				newCourses.add(managedCourse);
			}
		}
		existing.getCourses().addAll(newCourses);

		return studentRepository.save(existing);
	}

	@Override
	public void deleteStudent(Long id) {
		studentRepository.deleteById(id);
	}
}