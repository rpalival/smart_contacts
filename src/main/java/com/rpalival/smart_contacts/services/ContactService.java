package com.rpalival.smart_contacts.services;


import com.rpalival.smart_contacts.entities.Contact;

import java.util.List;

public interface ContactService {
    Contact save(Contact contact);
    List<Contact> getAll();
    Contact getById(String id);
    List<Contact> getByUserId(String userId);
    Contact update(Contact contact);
    void delete(String id);
    List<Contact> search(String name, String email, String phoneNumber);
}
