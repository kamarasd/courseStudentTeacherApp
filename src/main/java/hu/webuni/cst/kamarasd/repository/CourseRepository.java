package hu.webuni.cst.kamarasd.repository;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.cst.kamarasd.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

	Page<Course> findAll(Pageable pageable);

}
