package com.veera;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.veera.data.Employee;
import com.veera.service.MessageSender;

@SpringBootApplication
@RestController
public class SpringbootSqsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSqsApplication.class, args);
	}

	@Autowired
	private MessageSender messageSender;
	
	@GetMapping("/test")
	public void testmethod(@RequestParam String msg)
	{
		messageSender.send(msg);
	}
	
	@GetMapping("/postobject")
	public void postObject(@RequestBody Employee emp)
	{
		messageSender.sendObject(emp);
	}
}
