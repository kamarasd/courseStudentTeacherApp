package hu.webuni.cst.kamarasd.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import hu.webuni.cst.kamarasd.aspect.RecallNeptunIfFailed;

@Service
@RecallNeptunIfFailed
public class ConnectCentralDbService {
	
	private Random rand = new Random();
	
	public int getFreeSemesters(String neptunId) throws RuntimeException {
		int connectStatus = rand.nextInt(0, 2);
		connectStatus = 2;
		if(connectStatus == 2) {
			System.out.println("Connected to NEPTUN for student " + neptunId);
			return rand.nextInt(0, 13);
		} else {
			throw new RuntimeException("Connecting to NEPTUN failed for student " + neptunId); 
		}
	}
}