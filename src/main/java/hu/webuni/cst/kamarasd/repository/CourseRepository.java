package hu.webuni.cst.kamarasd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.cst.kamarasd.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{

}
