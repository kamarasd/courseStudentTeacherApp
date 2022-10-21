package hu.webuni.cst.kamarasd.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.webuni.cst.kamarasd.dto.CourseDto;
import hu.webuni.cst.kamarasd.model.Course;

@Mapper(componentModel = "spring")
public interface CourseMapper {
	CourseDto courseToDto(Course course);
	
	@Named("summary")
	@Mapping(ignore = true, target = "students")
	@Mapping(ignore = true, target = "teachers")
	CourseDto courseSummaryToDto(Course course);
	
	Course dtoToCourse(CourseDto courseDto);
	
	List<CourseDto> coursesToDtos(Iterable<Course> courses);
	
	@IterableMapping(qualifiedByName = "summary")
	List<CourseDto> coursesSummariesToDtos(Iterable<Course> findAll);
	
}
