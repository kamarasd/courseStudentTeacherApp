package hu.webuni.cst.kamarasd.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.cst.kamarasd.api.model.StudentDto;
import hu.webuni.cst.kamarasd.mapper.StudentMapper;
import hu.webuni.cst.kamarasd.model.Student;
import hu.webuni.cst.kamarasd.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentControllerOld {
	
	private final StudentRepository studentRepository;
	private final StudentMapper studentMapper;
	
	@GetMapping("/{id}")
	public StudentDto findById(@PathVariable("id") long id) {
		Student student = studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return studentMapper.studentToDto(student);
		
	}
	

}
