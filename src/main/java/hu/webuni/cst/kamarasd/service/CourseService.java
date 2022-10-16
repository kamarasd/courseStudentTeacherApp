package hu.webuni.cst.kamarasd.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import hu.webuni.cst.kamarasd.model.Course;
import hu.webuni.cst.kamarasd.repository.CourseRepository;
import hu.webuni.cst.kamarasd.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {
	
	
	TeacherRepository teacherRepository;
	CourseRepository courseRepository;
	
	
	public List<Course> findCourse(Predicate predicate, Pageable pageable) {
		
		Page<Course> coursePage = courseRepository.findAll(pageable);
		
		List<Course> course = coursePage.getContent();
		
		return course;
		
	}

}
