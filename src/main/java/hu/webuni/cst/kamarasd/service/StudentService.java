package hu.webuni.cst.kamarasd.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import hu.webuni.cst.kamarasd.config.CrontabConfig;
import hu.webuni.cst.kamarasd.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {
	
	private final StudentRepository studentRepository;
	private final ConnectCentralDbService connectCentralDbService;
	private final CrontabConfig crontabConfig;
	
	@Scheduled(cron = "${crontab.central.connect}")
	public void setFreeSemester() {
		System.out.println("Free semester update for each student");
		connectCentralDbService.getFreeSemesters();
	}

}
