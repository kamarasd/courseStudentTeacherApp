package hu.webuni.cst.kamarasd.web;

import java.io.IOException;

import javax.validation.Valid;

import hu.webuni.cst.kamarasd.api.model.StudentDto;
import hu.webuni.cst.kamarasd.api.model.TeacherDto;
import hu.webuni.cst.kamarasd.mapper.StudentMapper;
import hu.webuni.cst.kamarasd.model.Student;
import hu.webuni.cst.kamarasd.model.Teacher;
import hu.webuni.cst.kamarasd.repository.StudentRepository;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import hu.webuni.cst.kamarasd.api.StudentControllerApi;
import hu.webuni.cst.kamarasd.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class StudentController implements StudentControllerApi{
	
	private final StudentService studentService;
		
	@Override
	public ResponseEntity<String> uploadStudentProfilePic(Long id, @Valid String fileName, MultipartFile content) {
		try {
			studentService.saveProfilePicture(id, content.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Resource> getStudentPicture(Long id) {
		return ResponseEntity.ok(studentService.getProfilePicture(id));
	}
	
	
	
	
	
}
