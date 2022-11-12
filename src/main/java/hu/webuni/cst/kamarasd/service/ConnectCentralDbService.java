package hu.webuni.cst.kamarasd.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import hu.webuni.cst.kamarasd.aspect.RecallNeptunIfFailed;
import hu.webuni.cst.wsclient.SystemXmlImpWsService;
import hu.webuni.cst.wsclient.SystemXmlWs;

@Service
public class ConnectCentralDbService {

	private Random rand = new Random();

	@RecallNeptunIfFailed
	public int getFreeSemesters(String neptunId) {
		
		SystemXmlWs systemXmlWsPort = new SystemXmlImpWsService().getSystemXmlImpWsPort();
		return systemXmlWsPort.setFreeSemester(neptunId);
		
		/*int connectStatus = rand.nextInt(0, 3);

		if (connectStatus != 0) {
			System.out.println("Connected to NEPTUN for student: " + neptunId);
			return rand.nextInt(0, 13);
		} else {
			throw new RuntimeException("Connecting to NEPTUN failed!");
		}*/
	}
}