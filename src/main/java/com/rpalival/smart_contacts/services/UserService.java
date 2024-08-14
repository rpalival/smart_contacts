package com.rpalival.smart_contacts.services;

import com.rpalival.smart_contacts.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    Optional<User> getUserById(String id);

    Optional<User> updateUser(User user);

    void deleteUser(String id);

    boolean isUserExistByEmail(String email);

    boolean isUserExist(String userId);

    List<User> getAllUsers();

    // add more methods here related to user service or business logic as per the demand
}
