package hu.webuni.kamarasd;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NeptunWsClientApplication implements CommandLineRunner{
	
	public static void main(String[] args) {
		SpringApplication.run(NeptunWsClientApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Application started properly");
		
		System.out.println("Waiting for clients");
	}
}
