package com.tcg.training.serviceimpl.example;

import com.tcg.training.dto.CourseDTO;
import com.tcg.training.entity.example.Course;
import com.tcg.training.entity.example.Student;
import com.tcg.training.repository.example.CourseRepository;
import com.tcg.training.repository.example.StudentRepository;
import com.tcg.training.service.example.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService {
  @Autowired
  private CourseRepository courseRepository;
  
  @Autowired
  private StudentRepository studentRepository;

  @Override
  public Course createCourse(CourseDTO courseDTO) {
	Course course = new Course();
	course.setTitle(courseDTO.getTitle());
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
	public Course updateCourse(Long courseId, CourseDTO dto) {
		Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
		course.setTitle(dto.getTitle());

		Set<Long> studentIds = dto.getStudentIds();
		List<Student> studentsToAdd = studentRepository.findAllById(studentIds);

		for (Student student : studentsToAdd) {
			// Update both sides to maintain memory consistency
			student.getCourses().add(course);
			// course.getStudents().add(student);
		}

		// In this approach both student and course are update because student and
		// course both are managed entities means they are fetched from the database
		// and hibernate knows this entities So when we save the course object
		// automatically student object will flushed(save) and join table updates.
		// but todo this our service method should be annotated with @Transactional
		// annotation. save method of jpa is annotated with @Transactional annotation
		// so this is not annotated with @Transactional annotation
		return courseRepository.save(course);

	}
  
  @Override
  public void deleteCourse(Long id) {
    courseRepository.deleteById(id);
  }
}