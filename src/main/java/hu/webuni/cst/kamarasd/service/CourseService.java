package hu.webuni.cst.kamarasd.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import hu.webuni.cst.kamarasd.model.Course;
import hu.webuni.cst.kamarasd.model.QCourse;
import hu.webuni.cst.kamarasd.repository.CourseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {
	
	
	CourseRepository courseRepository;
	
	@Transactional
	public List<Course> findCourse(Predicate predicate, Pageable pageable) {
		
		Page<Course> coursePage = courseRepository.findAll(predicate, pageable);
		
		List<Course> courseContent = coursePage.getContent();
		
		BooleanExpression predicateCourse = QCourse.course.in(courseContent);
		
		List<Course> courses = courseRepository.findAll(predicateCourse, "Course.students", EntityGraphType.LOAD, Sort.unsorted());
		
		courses = courseRepository.findAll(predicateCourse, "Course.teachers", EntityGraphType.LOAD, pageable.getSort());
		
		return courses;
		
	}

}
