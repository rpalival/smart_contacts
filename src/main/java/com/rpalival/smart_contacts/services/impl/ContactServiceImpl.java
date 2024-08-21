package com.rpalival.smart_contacts.services.impl;

import com.rpalival.smart_contacts.entities.Contact;
import com.rpalival.smart_contacts.helpers.ResourceNotFoundException;
import com.rpalival.smart_contacts.repositories.ContactRepo;
import com.rpalival.smart_contacts.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;

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
        return contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact not found with given id: " + id));
    }

    @Override
    public List<Contact> getByUserId(String userId) {
        return contactRepo.findByUserId(userId);
    }

    @Override
    public Contact update(Contact contact) {
        return null;
    }

    @Override
    public void delete(String id) {
        var contact = contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact not found with given id: " + id));
        contactRepo.delete(contact);
    }

    @Override
    public List<Contact> search(String name, String email, String phoneNumber) {
        return List.of();
    }
}
