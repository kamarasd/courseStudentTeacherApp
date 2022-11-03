package hu.webuni.cst.kamarasd.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import hu.webuni.cst.kamarasd.config.CrontabConfig;
import hu.webuni.cst.kamarasd.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
	
	private final StudentRepository studentRepository;
	private final ConnectCentralDbService connectCentralDbService;
	
	@Scheduled(cron = "${crontab.central.connect}")
	public void setFreeSemester() {
		System.out.println("Free semester update for each student");
		try {
			studentRepository.findAll()
					.forEach(s -> {
						String neptunId = s.getNeptunId();
						s.setFreeSemesters(connectCentralDbService.getFreeSemesters());
						studentRepository.save(s);
						System.out.println("Connected to NEPTUN for student: " + neptunId + " Free semesters saved");
					});
		} catch (Exception e) {
			log.error("Connection error occured %s", e);
		}
	}
}
