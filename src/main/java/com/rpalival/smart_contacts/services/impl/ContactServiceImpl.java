package com.rpalival.smart_contacts.services.impl;

import com.rpalival.smart_contacts.entities.Contact;
import com.rpalival.smart_contacts.entities.User;
import com.rpalival.smart_contacts.helpers.ResourceNotFoundException;
import com.rpalival.smart_contacts.repositories.ContactRepo;
import com.rpalival.smart_contacts.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContactServiceImpl implements ContactService {
	
	private final ContactRepo contactRepo;
	
	@Autowired
	public ContactServiceImpl(ContactRepo contactRepo){
		this.contactRepo = contactRepo;
	}
	
	@Override
	public Contact save(Contact contact) {
		String contactId = UUID.randomUUID().toString();
		contact.setId(contactId);
		return contactRepo.save(contact);
	}
	
	@Override
	public List<Contact> getAll() {
		return contactRepo.findAll();
	}
	
	@Override
	public Contact getById(String id) {
		return contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact " +
				                                                                                "not found with given id: " + id));
	}
	
	@Override
	public List<Contact> getByUserId(String userId) {
		return contactRepo.findByUserId(userId);
	}
	
	@Override
	public Contact update(Contact contact) {
		var contactOld = contactRepo.findById(contact.getId()).orElseThrow(()-> new ResourceNotFoundException("Contact not found"));
		contactOld.setName(contact.getName());
		contactOld.setEmail(contact.getEmail());
		contactOld.setFavorite(contact.isFavorite());
		contactOld.setPhoneNumber(contact.getPhoneNumber());
		contactOld.setAddress(contact.getAddress());
		contactOld.setDescription(contact.getDescription());
		contactOld.setWebsiteLink(contact.getWebsiteLink());
		contactOld.setLinkedInLink(contact.getLinkedInLink());
		contactOld.setPicture(contact.getPicture());
		contactOld.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());
		
		return contactRepo.save(contactOld);
		
	}
	
	@Override
	public void delete(String id) {
		var contact = contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(
				"Contact not found with given id: " + id));
		contactRepo.delete(contact);
	}
	
	
	
	@Override
	public Page<Contact> getByUser(User user, int page, int size, String sortBy,
	                               String direction) {
		
		Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() :
				            Sort.by(sortBy).ascending();
		var pageable = PageRequest.of(page, size, sort);
		
		return contactRepo.findByUser(user, pageable);
	}
	
	@Override
	public Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user) {
		Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() :
				            Sort.by(sortBy).ascending();
		var pageable = PageRequest.of(page, size, sort);
		return contactRepo.findByUserAndNameContaining(user, nameKeyword, pageable);
	}
	
	@Override
	public Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user) {
		Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() :
				            Sort.by(sortBy).ascending();
		var pageable = PageRequest.of(page, size, sort);
		return contactRepo.findByUserAndEmailContaining(user, emailKeyword, pageable);
	}
	
	@Override
	public Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order, User user) {
		Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() :
				            Sort.by(sortBy).ascending();
		var pageable = PageRequest.of(page, size, sort);
		return contactRepo.findByUserAndPhoneNumberContaining(user, phoneNumberKeyword, pageable);
	}
}

