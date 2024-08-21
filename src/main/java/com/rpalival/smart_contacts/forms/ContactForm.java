package com.rpalival.smart_contacts.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactForm {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String description;
    private boolean favorite;
    private String websiteLink;
    private String linkedInLink;
    private MultipartFile ContactProfileImage;
}
