package com.rpalival.smart_contacts.controllers;

import com.rpalival.smart_contacts.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    // user dashboard
    @RequestMapping(value="/dashboard", method= RequestMethod.GET)
    public String userDashboard(){
        return "user/dashboard";
    }

    // user profile page
    @RequestMapping(value="/profile", method=RequestMethod.GET)
    // enter the loggedin user into this profile, you use principal
    public String userProfile(Model model, Authentication authentication){
        return "user/profile";
    }

}
