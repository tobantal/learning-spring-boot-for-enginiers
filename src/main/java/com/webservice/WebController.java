package com.webservice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webservice.model.Message;

//@RestController
public class WebController {
	
	@RequestMapping("/messsage")
	public Message getJsonMessage() {
		return new Message(new java.util.Date().toString());
	}

}
