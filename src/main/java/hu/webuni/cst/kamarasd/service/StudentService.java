package hu.webuni.cst.kamarasd.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.cst.kamarasd.config.CrontabConfig;
import hu.webuni.cst.kamarasd.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {
	
	private final StudentRepository studentRepository;
	private final ConnectCentralDbService connectCentralDbService;
	private final CrontabConfig crontabConfig;
	
	@Value("${student.content.pictures}")
	private String profilePicsFolder;

	@PostConstruct
	public void init() {
		try {
			Files.createDirectories(Path.of(profilePicsFolder));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@Scheduled(cron = "${crontab.central.connect}")
	public void setFreeSemester() {
		System.out.println("Free semester update for each student");
		connectCentralDbService.getFreeSemesters();
	}
	
	public void saveProfilePicture(long id, InputStream is) {
		if(!studentRepository.existsById(id))
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		try {
			Files.copy(is, getProfilePicPathForStudent(id), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private Path getProfilePicPathForStudent(long id) {
		Integer pictureId = Long.valueOf(id).intValue();
		return Paths.get(profilePicsFolder, pictureId.toString() + ".jpg");
	}

	public Resource getProfilePicture(long id) {
		FileSystemResource fileSystemResource = new FileSystemResource(getProfilePicPathForStudent(id));
		if(!fileSystemResource.exists())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return fileSystemResource;
	}

}
