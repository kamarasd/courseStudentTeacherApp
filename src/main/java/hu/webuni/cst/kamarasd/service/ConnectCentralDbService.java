package hu.webuni.cst.kamarasd.service;

import java.util.Random;

import hu.webuni.cst.wsclient.SystemXmlImpWsService;
import hu.webuni.cst.wsclient.SystemXmlWs;
import org.springframework.stereotype.Service;

import hu.webuni.cst.kamarasd.aspect.RecallNeptunIfFailed;


@Service
public class ConnectCentralDbService {

	private Random rand = new Random();

	@RecallNeptunIfFailed
	public int getFreeSemesters(String neptunId) {

		
		int connectStatus = rand.nextInt(0, 3);

		if (connectStatus != 0) {
			System.out.println("Connected to NEPTUN for student: " + neptunId);
			return new SystemXmlImpWsService()
					.getSystemXmlImpWsPort()
					.setFreeSemester(neptunId);
		} else {
			throw new RuntimeException("Connecting to NEPTUN failed!");
		}
	}
}