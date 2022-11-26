package hu.webuni.kamarasd.xml;

import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SystemXmlImpWs implements SystemXmlWs {

    private Random rand = new Random();

    @Override
    public Integer setFreeSemester(String neptunId) {
        log.info("A new request connected");
        Integer freeSemester = rand.nextInt(0, 10);
        log.info("Set " + freeSemester + " free semester for student: " + neptunId);
        return freeSemester;
    }


}
