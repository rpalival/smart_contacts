package com.rpalival.smart_contacts.controllers;

import com.rpalival.smart_contacts.entities.User;
import com.rpalival.smart_contacts.forms.UserForm;
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

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home page handler");
        // sending data to view
        model.addAttribute("name", "Substring Technologies");
        model.addAttribute("youtubeChannel", "Learn with harry");
        model.addAttribute("githubRepo", "https://github.com/rpalival/smart_contacts");
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
    @GetMapping("/login")
    public String login() {
        System.out.println("Contact page loading");
        return "login";
    }
    @GetMapping("/register")
    public String register(Model model) {

        // can plug default data as well
        // userForm.setName("Harry");
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);

        return "register";
    }

    // processing register / signing up the user
    // fetch form data
    // UserForm class and inside its object we will receive the form data

    // save to database
    // message = registration successful
    // redirect to login page

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
        user.setProfilePic("https://randomuser.me/api/portraits/women/31.jpg");

        User savedUser = userService.saveUser(user);
        System.out.println("User saved");

        // add the message
        Message message =
                Message.builder().content("Registration Successful").type(MessageType.green).build();
        session.setAttribute("message", message);

        return "redirect:/register";
    }
}
