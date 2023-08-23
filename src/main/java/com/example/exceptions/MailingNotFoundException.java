package com.example.exceptions;

public class MailingNotFoundException extends RuntimeException {

    public MailingNotFoundException(String message) {
        super(message);
    }
}
