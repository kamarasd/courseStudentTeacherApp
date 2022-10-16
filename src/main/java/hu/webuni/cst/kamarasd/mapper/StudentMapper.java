package hu.webuni.cst.kamarasd.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class StudentMapper {

	StudentDto studentToDto(Student student);
}
