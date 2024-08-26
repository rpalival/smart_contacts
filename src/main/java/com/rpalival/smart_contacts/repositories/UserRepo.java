package com.rpalival.smart_contacts.repositories;

import com.rpalival.smart_contacts.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Repositories have all the methods which are used to interact with the database
// these methods will come from JpaRepository inside which you have to give which entity
// you will work with and what is the data type of the id of that entity e.g., <User, String>
@Repository
public interface UserRepo extends JpaRepository<User, String> {
    // extra methods db related ops
    // custom query methods
    // custom finder methods
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);
	
	Optional<User> findByEmailToken(String token);
}
