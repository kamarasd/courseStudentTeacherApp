package webSocket;

import lombok.Data;

@Data
public class Messages {
	
	private String sender;
	private Long courseId;
	private String message;

}
