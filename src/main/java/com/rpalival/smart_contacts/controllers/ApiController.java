package com.rpalival.smart_contacts.controllers;

import com.rpalival.smart_contacts.entities.Contact;
import com.rpalival.smart_contacts.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	private final ContactService contactService;
	
	@Autowired
	public ApiController(ContactService contactService){
		this.contactService = contactService;
	}
	
	//EP - 1 : Returns all contact details of the given contact ID
	@GetMapping("/contacts/{contactId}")
	public Contact getContact(@PathVariable String contactId){
		return contactService.getById(contactId);
	}
}
