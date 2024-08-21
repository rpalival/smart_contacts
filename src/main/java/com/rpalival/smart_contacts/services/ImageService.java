package com.rpalival.smart_contacts.services;

import org.springframework.web.multipart.MultipartFile;


public interface ImageService {
    String uploadImage(MultipartFile contactProfileImage, String filename);
    String getUrlFromPublicId(String publicId);
}
