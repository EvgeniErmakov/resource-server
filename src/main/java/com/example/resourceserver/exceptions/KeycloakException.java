package com.example.resourceserver.exceptions;

public class KeycloakException extends RuntimeException {
    public KeycloakException(String message) {
        super(message);
    }
}
