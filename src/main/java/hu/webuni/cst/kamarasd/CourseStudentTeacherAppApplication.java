package hu.webuni.cst.kamarasd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.webuni.cst.kamarasd.service.InitDbService;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class CourseStudentTeacherAppApplication {
	
	public InitDbService initDbService;

	public static void main(String[] args) {
		SpringApplication.run(CourseStudentTeacherAppApplication.class, args);
	}
	
	public void run(String... args) throws Exception {
		initDbService.addInitData();
	}
	

}
