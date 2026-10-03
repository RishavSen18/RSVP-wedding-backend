package com.example.wedding.exception;

/**
 * Exception thrown when a requested resource (such as an RSVP response by ID) is not found.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
