package hu.webuni.cst.kamarasd.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.cst.kamarasd.model.Course;
import hu.webuni.cst.kamarasd.model.Student;
import hu.webuni.cst.kamarasd.model.Teacher;
import hu.webuni.cst.kamarasd.repository.CourseRepository;
import hu.webuni.cst.kamarasd.repository.StudentRepository;
import hu.webuni.cst.kamarasd.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class starterDbService {
	
	private final CourseRepository courseRepository;
	private final StudentRepository studentRepository;
	private final TeacherRepository teacherRepository;
	
	@Transactional
	public void deleteDb() {
		courseRepository.deleteAll();
		studentRepository.deleteAll();
		teacherRepository.deleteAll();
		
	}
	
	@Transactional
	public void addInitData() {
		
		Student s1 = createNewStudent("Kiss Aladár", LocalDate.of(1997, 11, 11), 2);
		
		Teacher t1 = createNewTeacher("Nagy Sándor", LocalDate.of(1982, 01, 01));
		
		createNewCourse("Magyar nyelvtan", Arrays.asList(t1), Arrays.asList(s1));
		
	}
	
	private Course createNewCourse(String name, List<Teacher> teacher, List<Student> student) {
		return courseRepository.save(Course.
				builder()
					.name(name)
					.teachers(new HashSet<>(teacher))
					.students(new HashSet<>(student))
				.build());
	}
	
	private Student createNewStudent(String name, LocalDate birthdate, int semester) {
			return studentRepository.save(Student
					.builder()
						.name(name)
						.birthdate(birthdate)
						.semester(semester)
					.build());
	}
	
	private Teacher createNewTeacher(String name, LocalDate birthdate) {
		return teacherRepository.save(Teacher
				.builder()
					.name(name)
					.birthdate(birthdate)
				.build());
	}

}
