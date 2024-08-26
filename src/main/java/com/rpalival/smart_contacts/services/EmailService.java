package com.rpalival.smart_contacts.services;

public interface EmailService {
	void sendEmail(String to, String subject, String body);
	void sendEmailWithHtml();
	void sendEmailWithAttachment();
}
