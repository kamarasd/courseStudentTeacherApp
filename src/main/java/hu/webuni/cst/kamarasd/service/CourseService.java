package hu.webuni.cst.kamarasd.service;

import java.time.OffsetDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import hu.webuni.cst.kamarasd.model.Course;
import hu.webuni.cst.kamarasd.model.HistoryData;
import hu.webuni.cst.kamarasd.model.QCourse;
import hu.webuni.cst.kamarasd.repository.CourseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {
	
	
	private final CourseRepository courseRepository;
	private final AuditService auditService;
	
	@Transactional
	@Cacheable("pagedCoursesWithFullSearch")
	public List<Course> findCourse(Predicate predicate, Pageable pageable) {
		
		Page<Course> coursePage = courseRepository.findAll(predicate, pageable);
		
		List<Course> courseContent = coursePage.getContent();
		
		BooleanExpression predicateCourse = QCourse.course.in(courseContent);
		
		List<Course> courses = courseRepository.findAll(predicateCourse, "Course.students", EntityGraphType.LOAD, Sort.unsorted());
		
		courses = courseRepository.findAll(predicateCourse, "Course.teachers", EntityGraphType.LOAD, pageable.getSort());
		
		return courses;
		
	}
	
	public List<HistoryData<Course>> getCourseHistory(long id) {
		return auditService.getCourseHistory(id);
	}
	
	public Course getThisHistory(long id, OffsetDateTime date) {
		return auditService.getHistoryForDefinedClass(id, date);
	}
}
