package com.rpalival.smart_contacts.controllers;

import com.rpalival.smart_contacts.entities.User;
import com.rpalival.smart_contacts.helpers.Helper;
import com.rpalival.smart_contacts.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication){
        if(authentication == null) return;
        System.out.println("Adding logged in user information to the model");

        String username = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User logged in: {}", username);

        User user = userService.getUserByEmail(username);

        logger.info(String.valueOf(user));
        logger.info(user.getName());
        logger.info(user.getEmail());
        model.addAttribute("loggedInUser", user);

    }
}
