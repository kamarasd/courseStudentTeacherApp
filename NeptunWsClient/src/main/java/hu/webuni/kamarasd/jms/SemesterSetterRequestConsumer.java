package hu.webuni.kamarasd.jms;

import hu.webuni.jms.dto.SemesterSetterRequest;
import hu.webuni.jms.dto.SemesterSetterResponse;
import hu.webuni.kamarasd.xml.SystemXmlWs;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.jms.Topic;

@Component
@RequiredArgsConstructor
public class SemesterSetterRequestConsumer {
    private final SystemXmlWs systemXmlWs;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = "semester_setter_requests")
    public void semesterSetterMessage(Message<SemesterSetterRequest> message) {
        String neptunId = message.getPayload().getNeptunId();
        int freeSemesters = systemXmlWs.setFreeSemester(neptunId);

        SemesterSetterResponse semesterSetterResponse = new SemesterSetterResponse();
        semesterSetterResponse.setNumFreeSemester(freeSemesters);
        semesterSetterResponse.setNeptunId(neptunId);

        jmsTemplate.convertAndSend((Topic)message.getHeaders().get(JmsHeaders.REPLY_TO),
                semesterSetterResponse);
    }
}
