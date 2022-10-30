package hu.webuni.cst.kamarasd.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.cst.kamarasd.aspect.RecallNeptunIfFailed;
import hu.webuni.cst.kamarasd.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConnectCentralDbService {
	
	@Autowired
	private StudentRepository studentRepository;
	private Random rand = new Random();
	
	@RecallNeptunIfFailed
	public void getFreeSemesters() {
		int connectStatus = rand.nextInt(0, 2);
		
		try {
			if(connectStatus != 0) {
				studentRepository.findAll()
				.forEach(s -> {
					String neptunId = s.getNeptunId();
					s.setFreeSemesters(rand.nextInt(0, 13));
					studentRepository.save(s);
					System.out.println("Connected to NEPTUN for student: " + neptunId + " Free semesters saved");
					}
				);
			} else {
				throw new RuntimeException("Connecting to NEPTUN failed!");
			}
			
		} catch (Exception e) {
			log.error("Connection error occured. ", e);
		}
		
	}
}