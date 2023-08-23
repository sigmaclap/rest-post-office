package com.example.exceptions;

public class TrackingNotFoundException extends RuntimeException {
    public TrackingNotFoundException(String message) {
        super(message);
    }
}
