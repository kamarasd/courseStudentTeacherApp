package hu.webuni.cst.kamarasd.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.cst.kamarasd.api.model.TeacherDto;
import hu.webuni.cst.kamarasd.mapper.TeacherMapper;
import hu.webuni.cst.kamarasd.model.Teacher;
import hu.webuni.cst.kamarasd.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/teachers")
public class TeacherControllerOld {
	
	private final TeacherRepository teacherRepository;
	private final TeacherMapper teacherMapper;
	
	@GetMapping("/{id}")
	public TeacherDto findById(@PathVariable("id") Long id) {
		Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return teacherMapper.teacherToDto(teacher);
	}

}
