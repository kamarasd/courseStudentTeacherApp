package hu.webuni.cst.kamarasd.dto;

import java.util.List;

import lombok.Data;

@Data
public class CourseDto {

	private Long id;
	private String name;
	private List<StudentDto> students;
	private List<TeacherDto> teachers;
}
