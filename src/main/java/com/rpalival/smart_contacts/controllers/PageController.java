package com.rpalival.smart_contacts.controllers;

import com.rpalival.smart_contacts.entities.User;
import com.rpalival.smart_contacts.forms.UserForm;
import com.rpalival.smart_contacts.helpers.AppConstants;
import com.rpalival.smart_contacts.helpers.Message;
import com.rpalival.smart_contacts.helpers.MessageType;
import com.rpalival.smart_contacts.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

    //this is field injection (not recommended), constructor injection is recommended
    @Autowired
    private UserService userService;

    // to implement constructor injection do as below
    //    private final UserService userService;
    //
    //    public PageController(UserService userService) {
    //        this.userService = userService;
    //    }
    @GetMapping("/")
    public String index(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home() {
        System.out.println("Home page view");
        return "home";
    }
    // about routes
    @RequestMapping("/about")
    public String aboutPage() {
        System.out.println("About page loading");
        return "about";
    }

    // services
    @RequestMapping("/services")
    public String servicesPage() {
        System.out.println("Services page loading");
        return "services";
    }

    // contact page
    @GetMapping("/contact")
    public String contact() {
        System.out.println("Contact page loading");
        return "contact";
    }

    // login view
    @GetMapping("/login")
    public String login() {
        System.out.println("login page loading");
        return "login";
    }


    // SignUp/ register view endpoint
    @GetMapping("/register")
    public String register(Model model) {

        // can plug default data as well
        // userForm.setName("Harry");
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);

        return "register";
    }
    
    // registration processing endpoint
    @RequestMapping(value="/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm,
                                  BindingResult rBindingResult, HttpSession session){

        if(rBindingResult.hasErrors()) return "register";

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic(AppConstants.DEFAULT_USER_PROFILE_PIC);
        user.setEnabled(false);

        User savedUser = userService.saveUser(user);
        System.out.println("User saved");

        // add the message
        Message message =
                Message.builder().content("Registration Successful").type(MessageType.green).build();
        session.setAttribute("message", message);

        return "redirect:/login";
    }
}
