package hu.webuni.cst.kamarasd.service;

import java.sql.Array;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import hu.webuni.cst.kamarasd.model.*;
import hu.webuni.cst.kamarasd.repository.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Service
public class StarterDbService {
	
	private final CourseRepository courseRepository;
	private final StudentRepository studentRepository;
	private final TeacherRepository teacherRepository;
	private final JdbcTemplate jdbcTemplate;
	private final TimetableRepository timetableRepository;
	private final PasswordEncoder passwordEncoder;
	private final CstUserRepository cstUserRepository;
	
	@Transactional
	public void deleteDb() {
		timetableRepository.deleteAll();
		courseRepository.deleteAll();
		studentRepository.deleteAll();
		teacherRepository.deleteAll();
		cstUserRepository.deleteAll();

		jdbcTemplate.update("DELETE FROM course_aud");
		jdbcTemplate.update("DELETE FROM student_aud");
		jdbcTemplate.update("DELETE FROM teacher_aud");
		jdbcTemplate.update("DELETE FROM timetable_aud");
		jdbcTemplate.update("DELETE FROM cst_user_details_aud");

	}
	
	@Transactional
	public void addInitData() {
		
		Student s1 = createNewStudent("Kiss Aladár", LocalDate.of(1997, 11, 11), 2, "ABC123", 0, "s1", "pass");
		Student s2 = createNewStudent("Kovakövi Enikő", LocalDate.of(0003, 01, 06), 25, "AAA000", 0, "s2", "pass");
		
		Teacher t1 = createNewTeacher("Nagy Sándor", LocalDate.of(1982, 01, 01), "t1", "pass");
		Teacher t2 = createNewTeacher("Kovakövi Frédi", LocalDate.of(0001, 05, 06), "t2", "pass");
		
		Course c1 = createNewCourse("Magyar nyelvtan", Arrays.asList(t1), Arrays.asList(s1), 2022, Semester.SemesterType.FALL);
		Course c2 = createNewCourse("Hittan", Arrays.asList(t2), Arrays.asList(s1,s2), 2022, Semester.SemesterType.SPRING);

		createNewTimetable(LocalDate.of(2022, 11, 04),
				LocalTime.of(12, 00), LocalTime.of(13, 45), c1);

		createNewTimetable(LocalDate.of(2022, 11, 11),
				LocalTime.of(12, 00), LocalTime.of(13, 45), c2);
	}
	
	private Course createNewCourse(String name, List<Teacher> teacher, List<Student> student, int year, Semester.SemesterType semesterType) {
		return courseRepository.save(Course.
				builder()
					.name(name)
					.teachers(new HashSet<>(teacher))
					.students(new HashSet<>(student))
						.semester(
								Semester.builder()
										.year(year)
										.semesterType(semesterType)
										.build()
						)
				.build());
	}
	
	private Student createNewStudent(String name, LocalDate birthdate, int semester, String neptun, Integer balance, String user, String password) {
		return studentRepository.save(Student
					.builder()
						.name(name)
						.birthdate(birthdate)
						.semester(semester)
						.neptunId(neptun)
					    .balance(balance)
						.username(user)
						.password(passwordEncoder.encode(password))
					.build());
	}
	
	private Teacher createNewTeacher(String name, LocalDate birthdate, String username, String password) {
		return teacherRepository.save(Teacher
				.builder()
					.name(name)
					.birthdate(birthdate)
						.username(username)
						.password(passwordEncoder.encode(password))
				.build());
	}

	private void createNewTimetable(LocalDate day, LocalTime from, LocalTime to, Course course) {
		course.addTimetable(timetableRepository.save(
				Timetable.builder()
						.lessonDate(day)
						.lessonStart(from)
						.lessonEnd(to)
						.build()
		));
	}


}
