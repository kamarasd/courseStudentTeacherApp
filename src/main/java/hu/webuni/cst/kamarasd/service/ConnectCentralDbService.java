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

	private Random rand = new Random();
	
	@RecallNeptunIfFailed
	public int getFreeSemesters() {
		int connectStatus = rand.nextInt(0, 3);

		if(connectStatus != 0) {
				return rand.nextInt(0, 13);
		} else {
			throw new RuntimeException("Connecting to NEPTUN failed!");
		}
	}
}