package hu.webuni.cst.kamarasd.mapper;

import org.mapstruct.Mapper;

import hu.webuni.cst.kamarasd.api.model.StudentDto;
import hu.webuni.cst.kamarasd.model.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {
	StudentDto studentToDto(Student student);
}
