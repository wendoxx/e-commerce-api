package org.example.ecommerce.infra.config.exception;

public class UsernameIsNotAvailableException extends RuntimeException {
    public UsernameIsNotAvailableException(String message) {
        super("Oh no! Something went wrong! Here is the problem: " + message);
    }
}
