package hu.webuni.cst.kamarasd.mapper;

import org.mapstruct.Mapper;

import hu.webuni.cst.kamarasd.api.model.TeacherDto;
import hu.webuni.cst.kamarasd.model.Teacher;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
	TeacherDto teacherToDto(Teacher teacher);
}
