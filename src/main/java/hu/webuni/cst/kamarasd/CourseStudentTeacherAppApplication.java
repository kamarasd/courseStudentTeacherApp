package hu.webuni.cst.kamarasd;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.webuni.cst.kamarasd.service.starterDbService;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class CourseStudentTeacherAppApplication implements CommandLineRunner{
	
	private final starterDbService sDService;

	public static void main(String[] args) {
		SpringApplication.run(CourseStudentTeacherAppApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		sDService.deleteDb();
		sDService.addInitData();
	}
	

}
