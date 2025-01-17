package org.example.ecommerce.infra.config.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super("Oh no! Something went wrong! Here is the problem: " + message);
    }
}
