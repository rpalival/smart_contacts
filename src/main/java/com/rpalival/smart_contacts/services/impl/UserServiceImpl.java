package com.rpalival.smart_contacts.services.impl;

import com.rpalival.smart_contacts.entities.User;
import com.rpalival.smart_contacts.helpers.AppConstants;
import com.rpalival.smart_contacts.helpers.ConfigHelper;
import com.rpalival.smart_contacts.helpers.Helper;
import com.rpalival.smart_contacts.helpers.ResourceNotFoundException;
import com.rpalival.smart_contacts.repositories.UserRepo;
import com.rpalival.smart_contacts.services.EmailService;
import com.rpalival.smart_contacts.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// this annotation will create a service object and understand that it's a service
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private EmailService emailService;

    @Autowired
    private ConfigHelper configHelper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private Helper helper;

    @Override
    public User saveUser(User user) {
        // user id: dynamically generate it
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setPassword(configHelper.passwordEncoder().encode(user.getPassword()));

        //set the user role

        user.setRoleList(List.of(AppConstants.ROLE_USER));
        logger.info(user.getProvider().toString());
        String emailToken= UUID.randomUUID().toString();
        user.setEmailToken(emailToken);
        User savedUser = userRepo.save(user);
        String emailLink = helper.getLinkForEmailVerification(emailToken);
        emailService.sendEmail(savedUser.getEmail(), "Verify Account : Smart Contacts Email Verification", emailLink);
        
        return savedUser;
        
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User user2 = userRepo.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());

        // save the user in database
        User save = userRepo.save(user2);
        return Optional.ofNullable(save);
    }

    @Override
    public void deleteUser(String id) {
        User user2 = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User " +
                "not found"));
        userRepo.delete(user2);
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public boolean isUserExist(String userId) {
        User user2 = userRepo.findById(userId).orElse(null);
        return user2!=null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }
}
