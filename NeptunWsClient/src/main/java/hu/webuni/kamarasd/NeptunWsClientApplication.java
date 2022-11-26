package hu.webuni.kamarasd;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class NeptunWsClientApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NeptunWsClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Application started properly");
        log.info("Waiting for clients");
    }
}
