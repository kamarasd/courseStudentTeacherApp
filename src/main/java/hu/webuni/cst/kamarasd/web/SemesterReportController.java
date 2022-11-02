package hu.webuni.cst.kamarasd.web;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.cst.kamarasd.model.SemesterReport;
import hu.webuni.cst.kamarasd.repository.CourseRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class SemesterReportController {
	
	public final CourseRepository courseRepository;
	
	@Async
	@GetMapping("/averageSemesters")
	public CompletableFuture<List<SemesterReport>> getAverageSemesterForStudents() {
		System.out.println("The report generating started at " + Thread.currentThread().getName());
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			
		}
		
		return CompletableFuture.completedFuture(courseRepository.getAverageSemesters());
	}

}
