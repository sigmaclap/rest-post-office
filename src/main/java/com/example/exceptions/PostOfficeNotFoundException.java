package com.example.exceptions;

public class PostOfficeNotFoundException extends RuntimeException {
    public PostOfficeNotFoundException(String message) {
        super(message);
    }
}
