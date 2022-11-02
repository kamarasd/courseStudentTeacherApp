package webSocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
	
	private final SimpMessagingTemplate simpMessage;
	
	@MessageMapping("/chat")
	public void sendMessage(Messages msg) {
		simpMessage.convertAndSend("/topic/courseChat" + msg.getCourseId(), String.format("%s, %s", msg.getSender(), msg.getMessage()));
		
	}
	
	

}
