package com.rpalival.smart_contacts.forms;

import com.rpalival.smart_contacts.validators.ValidFile;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ContactForm {
    @NotBlank(message = "Contact name is required")
    @Size(min=3, message = "Give 3+ characters")
    private String name;

    @Email(message = "Invalid Email Address")
    private String email;

    @NotBlank(message = "Contact Number is required")
    @Pattern(regexp="^[0-9]{10}", message="Invalid Phone Number, Please enter 10 digit number")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    private String address;

    private String description;
    private boolean favorite;
    private String websiteLink;
    private String linkedInLink;
    
    @ValidFile
    private MultipartFile contactProfileImage;
    private String picture;
    
}
