package com.rpalival.smart_contacts.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

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
    public String register() {
        System.out.println("Contact page loading");
        return "register";
    }


}
