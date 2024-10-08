package com.rpalival.smart_contacts.controllers;

import com.rpalival.smart_contacts.entities.User;
import com.rpalival.smart_contacts.helpers.Message;
import com.rpalival.smart_contacts.helpers.MessageType;
import com.rpalival.smart_contacts.repositories.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepo userRepo;
	
	@GetMapping("/verify-email")
	public String verifyEmail(
			@RequestParam("token") String token,
			HttpSession session
	){
		User user = userRepo.findByEmailToken(token).orElse(null);
		if(user != null){
			if(user.getEmailToken().equals(token)){
				user.setEmailVerified(true);
				user.setEnabled(true);
				userRepo.save(user);
				session.setAttribute("message", Message.builder().content("Email verified successfully. You can login now.").type(MessageType.green).build());
				return "success_page";
			}
			
			session.setAttribute("message", Message.builder().content("Invalid Token! Email not verified.").type(MessageType.red).build());
			return "error_page";
		}
		session.setAttribute("message", Message.builder().content("Invalid Token! Email not verified.").type(MessageType.red).build());
		return "error_page";
	}
}
