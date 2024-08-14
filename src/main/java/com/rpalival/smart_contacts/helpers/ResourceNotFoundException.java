package com.rpalival.smart_contacts.helpers;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(){
        super("Resource not found");
    }
}
