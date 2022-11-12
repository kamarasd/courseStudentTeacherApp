package hu.webuni.cst.kamarasd;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

import hu.webuni.cst.kamarasd.service.StarterDbService;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
@EnableCaching
@EnableAsync
public class CourseStudentTeacherAppApplication implements CommandLineRunner{
	
	private final StarterDbService sDService;

	public static void main(String[] args) {

		SpringApplication.run(CourseStudentTeacherAppApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		sDService.deleteDb();
		sDService.addInitData();
	}
	

}
