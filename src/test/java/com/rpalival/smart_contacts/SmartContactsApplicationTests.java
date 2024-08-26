package com.rpalival.smart_contacts;

import com.rpalival.smart_contacts.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmartContactsApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	private EmailService service;
	
	@Test
	void sendEmailTest(){
		service.sendEmail(
				"rajpalival10@gmail.com",
				"Just testing email service",
				"This is smart contacts app"
		);
	}

}
