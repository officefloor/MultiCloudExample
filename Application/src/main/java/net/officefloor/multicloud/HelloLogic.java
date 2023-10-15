package net.officefloor.multicloud;

import net.officefloor.web.HttpPathParameter;
import net.officefloor.web.ObjectResponse;

public class HelloLogic {

	public void hello(@HttpPathParameter("name") String name, ObjectResponse<Message> response) {
		response.send(new Message("Hello " + name));
	}

	public static class Message {
		private String message;

		public Message(String message) {
			this.message = message;
		}
		
		public String getMessage() {
			return this.message;
		}
	}
}