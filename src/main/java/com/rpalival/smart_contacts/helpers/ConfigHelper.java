package com.rpalival.smart_contacts.helpers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ConfigHelper {

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
