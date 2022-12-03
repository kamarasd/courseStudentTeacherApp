package hu.webuni.cst.kamarasd.service;

import java.util.Random;

import hu.webuni.cst.kamarasd.repository.StudentRepository;
import hu.webuni.cst.wsclient.SystemXmlImpWsService;
import hu.webuni.cst.wsclient.SystemXmlWs;
import hu.webuni.jms.dto.SemesterSetterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import hu.webuni.cst.kamarasd.aspect.RecallNeptunIfFailed;

import javax.jms.Topic;


@Service
@RequiredArgsConstructor
public class ConnectCentralDbService {

	private Random rand = new Random();

	private static final String FREE_SEMESTER_REQ = "semester_setter_requests";
	public static final String FREE_SEMESTER_RESP = "semester_setter_responses";

	private final JmsTemplate jmsTemplate;
	private final StudentRepository studentRepository;

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

	public void askFreeSemesterSetter(String neptunId) {
		SemesterSetterRequest semesterSetterRequest = new SemesterSetterRequest();
		semesterSetterRequest.setNeptunId(neptunId);

		Topic topic = jmsTemplate.execute(session -> session.createTopic(FREE_SEMESTER_RESP));


		jmsTemplate.convertAndSend(FREE_SEMESTER_REQ, semesterSetterRequest, message -> {
			message.setJMSReplyTo(topic);
			return message;
		});
	}
}