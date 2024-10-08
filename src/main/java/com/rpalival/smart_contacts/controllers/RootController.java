package com.rpalival.smart_contacts.controllers;

import com.rpalival.smart_contacts.entities.User;
import com.rpalival.smart_contacts.helpers.Helper;
import com.rpalival.smart_contacts.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {

    
    private final UserService userService;
    
    @Autowired
    public RootController(UserService userService){
        this.userService = userService;
    }

    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication){
        if(authentication == null) return;
        
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        
        model.addAttribute("loggedInUser", user);

    }
}
