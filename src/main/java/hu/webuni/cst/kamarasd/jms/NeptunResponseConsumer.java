package hu.webuni.cst.kamarasd.jms;

import hu.webuni.cst.kamarasd.service.ConnectCentralDbService;
import hu.webuni.cst.kamarasd.service.StudentService;
import hu.webuni.jms.dto.SemesterSetterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NeptunResponseConsumer {

    private final StudentService studentService;

    @JmsListener(destination = ConnectCentralDbService.FREE_SEMESTER_RESP, containerFactory = "neptunFactory")
    public void freeSemesterSetterResponse(SemesterSetterResponse resp) {
        studentService.setFreeSemester(resp.getNeptunId(), resp.getNumFreeSemester());
    }
}
