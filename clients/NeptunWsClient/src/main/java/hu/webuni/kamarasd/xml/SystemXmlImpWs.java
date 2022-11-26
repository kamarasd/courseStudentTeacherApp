package hu.webuni.kamarasd.xml;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class SystemXmlImpWs implements SystemXmlWs {
	
	private Random rand = new Random();

	@Override
	public Integer setFreeSemester(String neptunId) {
		System.out.println("A new request connected");
		Integer freeSemester = rand.nextInt(0, 10);
		System.out.println("Set " + freeSemester + " free semester for student: "+ neptunId);
		return freeSemester;
	}
	
	

}
