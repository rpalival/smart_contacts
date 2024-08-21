package com.rpalival.smart_contacts.controllers;

import com.rpalival.smart_contacts.entities.Contact;
import com.rpalival.smart_contacts.entities.User;
import com.rpalival.smart_contacts.forms.ContactForm;
import com.rpalival.smart_contacts.helpers.Helper;
import com.rpalival.smart_contacts.helpers.Message;
import com.rpalival.smart_contacts.helpers.MessageType;
import com.rpalival.smart_contacts.services.ContactService;
import com.rpalival.smart_contacts.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private final ContactService contactService;
    private final UserService userService;

    @Autowired
    public ContactController(ContactService contactService, UserService userService){
        this.contactService = contactService;
        this.userService = userService;
    }

    @RequestMapping("/add")
    public String addContactView(Model model) {
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result, Authentication authentication, HttpSession session){

        // validate form
        // process the contact picture
        // set the contact picture url
        // set the message for confirmation
        if(result.hasErrors()){
            Message errorMessage = Message.builder().
                    content("Please correct the following errors").
                    type(MessageType.red).
                    build();
            session.setAttribute("message", errorMessage);
            return "user/add_contact";
        }

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        Contact contact = new Contact();

        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setFavorite(contactForm.isFavorite());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setUser(user);


        contactService.save(contact);
        System.out.println(contactForm);

        Message message = Message.builder().content("You have successfully added a new contact").type(MessageType.green).build();
        session.setAttribute("message", message);
        return "redirect:/user/contacts/add";
    }
}
