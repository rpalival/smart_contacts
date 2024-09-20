package com.rpalival.smart_contacts;

import com.rpalival.smart_contacts.config.AppConfig;
import com.rpalival.smart_contacts.entities.User;
import com.rpalival.smart_contacts.helpers.AppConstants;
import com.rpalival.smart_contacts.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;
//import org.springframework.context.annotation.PropertySource;
//@PropertySource("application-${env}.properties")

@SpringBootApplication
public class SmartContactsApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(SmartContactsApplication.class, args);
	}
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setUserId(UUID.randomUUID().toString());
		user.setName("admin");
		user.setEmail("admin@gmail.com");
		user.setPassword(passwordEncoder.encode("admin"));
		user.setRoleList(List.of(AppConstants.ROLE_USER));
		user.setEmailVerified(true);
		user.setEnabled(true);
		user.setAbout("This is dummy user created initially");
		user.setPhoneVerified(true);
		
		userRepo.findByEmail("admin@gmail.com").ifPresentOrElse(user1 -> {},() -> {
			userRepo.save(user);
			System.out.println("user created");
		});
		
	}
}