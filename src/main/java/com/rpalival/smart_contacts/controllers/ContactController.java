package com.rpalival.smart_contacts.controllers;

import com.rpalival.smart_contacts.entities.Contact;
import com.rpalival.smart_contacts.entities.User;
import com.rpalival.smart_contacts.forms.ContactForm;
import com.rpalival.smart_contacts.forms.ContactSearchForm;
import com.rpalival.smart_contacts.helpers.AppConstants;
import com.rpalival.smart_contacts.helpers.Helper;
import com.rpalival.smart_contacts.helpers.Message;
import com.rpalival.smart_contacts.helpers.MessageType;
import com.rpalival.smart_contacts.services.ContactService;
import com.rpalival.smart_contacts.services.ImageService;
import com.rpalival.smart_contacts.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {
	
	// Concept Start: Constructor Injection
	private final Logger logger = LoggerFactory.getLogger(ContactController.class);
	private final ContactService contactService;
	private final UserService userService;
	private final ImageService imageService;
	
	@Autowired
	public ContactController(ContactService contactService, UserService userService,
	                         ImageService imageService) {
		this.contactService = contactService;
		this.userService = userService;
		this.imageService = imageService;
	}
	// Concept End: Constructor Injection
	
	// Route 1: GET Mapping by default -> To get the contact form field values -> endpoint: /user/contacts/add GET
	@RequestMapping("/add")
	public String addContactView(Model model) {
		ContactForm contactForm = new ContactForm();
		model.addAttribute("contactForm", contactForm);
		return "user/add_contact";
	}
	
	// Route 2: -> To save the contact to the Database -> endpoint: /user/contacts/add POST
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result
			, Authentication authentication, HttpSession session) {
		
		// Logic: Redirect to same page if form has errors
		if (result.hasErrors()) {
			Message errorMessage = Message.builder().
					                       content("Please correct the following errors").
					                       type(MessageType.red).
					                       build();
			session.setAttribute("message", errorMessage);
			return "user/add_contact";
		}
		
		// Logic: To get the current user of our application
		String username = Helper.getEmailOfLoggedInUser(authentication);
		User user = userService.getUserByEmail(username);
		
		// Logic: To store the filename separately
		String filename = UUID.randomUUID().toString();
		String fileURL = imageService.uploadImage(contactForm.getContactProfileImage(), filename);
		
		Contact contact = new Contact();
		
		// Logic: Setter methods of contact entity in which getter methods of contact entity are
		// being passed
		contact.setName(contactForm.getName());
		contact.setEmail(contactForm.getEmail());
		contact.setFavorite(contactForm.isFavorite());
		contact.setPhoneNumber(contactForm.getPhoneNumber());
		contact.setAddress(contactForm.getAddress());
		contact.setDescription(contactForm.getDescription());
		contact.setWebsiteLink(contactForm.getWebsiteLink());
		contact.setLinkedInLink(contactForm.getLinkedInLink());
		contact.setPicture(fileURL);
		contact.setCloudinaryImagePublicId(filename);
		contact.setUser(user);
		
		// This saves it to database
		contactService.save(contact);
		
		Message message =
				Message.builder().content("You have successfully added a new contact").type(MessageType.green).build();
		session.setAttribute("message", message);
		return "redirect:/user/contacts/add";
	}
	
	// Route 3: -> View contacts -> endpoint: /user/contacts GET
	@RequestMapping
	public String viewContacts(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
			@RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
			@RequestParam(value = "direction", defaultValue = "asc") String direction,
			Model model, Authentication authentication
	) {
		
		String username = Helper.getEmailOfLoggedInUser(authentication);
		User user = userService.getUserByEmail(username);
		
		Page<Contact> pageContact = contactService.getByUser(user, page, size, sortBy, direction);
		model.addAttribute("pageContact", pageContact);
		model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
		model.addAttribute("contactSearchForm", new ContactSearchForm());
		
		
		return "user/contacts";
	}
	
	// Route 4: -> Search Handler -> endpoint: /user/contacts/search GET
	@RequestMapping("/search")
	public String searchHandler(
			@ModelAttribute ContactSearchForm contactSearchForm,
			@RequestParam(value="size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
			@RequestParam(value="page", defaultValue = "0") int page,
			@RequestParam(value="sortBy", defaultValue = "name") String sortBy,
			@RequestParam(value="direction", defaultValue = "asc") String direction,
			Model model,
			Authentication authentication
	) {
		logger.info("field {} keyword {}", contactSearchForm.getField(), contactSearchForm.getValue());
		var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));
		
		Page<Contact> pageContact = null;
		if (contactSearchForm.getField().equalsIgnoreCase("name")){
			pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction, user);
		} else if (contactSearchForm.getField().equalsIgnoreCase("email")){
			pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction, user);
		} else if (contactSearchForm.getField().equalsIgnoreCase("phone")){
			pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy, direction, user);
		}
		logger.info("pageContact {}", pageContact);
		model.addAttribute("pageContact", pageContact);
		model.addAttribute("contactSearchForm", contactSearchForm);
		model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
		
		return "user/search";
	}
	
	// Route 5: -> Delete Contact -> endpoint: /user/contacts/delete:{contactId} GET
	@RequestMapping("/delete/{contactId}")
	public String deleteContact(@PathVariable("contactId") String contactId, HttpSession session){
		Contact contact = contactService.getById(contactId);
		
		contactService.delete(contactId);
		logger.info("contact {} with id:{}, deleted", contact.getName(), contactId);
		Message message =
				Message.builder().content("You have successfully deleted a contact!!").type(MessageType.green).build();
		session.setAttribute("message", message);
		return "redirect:/user/contacts";
	}
}
