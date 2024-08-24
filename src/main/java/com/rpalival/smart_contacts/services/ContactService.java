package com.rpalival.smart_contacts.services;


import com.rpalival.smart_contacts.entities.Contact;
import com.rpalival.smart_contacts.entities.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContactService {
    
    Contact save(Contact contact);
    
    List<Contact> getAll();
    Contact getById(String id);
    List<Contact> getByUserId(String userId);
    Page<Contact> getByUser(User user, int page, int size, String sortField, String sortDirection);
    
    Contact update(Contact contact);
    void delete(String id);
    
    Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user);
    Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user);
    Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order, User user);
    
}
