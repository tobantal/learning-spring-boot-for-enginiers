package com.webservice.model;

public class Message {
	private final String message;
	
	@Override
	public String toString() {
		return String.format("Message{message='%s'}", message);
	}

	public String getMessage() {
		return message;
	}

	public Message(String message) {
		this.message = message;
	}
	
	
}