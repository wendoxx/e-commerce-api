package org.example.ecommerce.infra.config.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super("Oh no! Something went wrong! Here is the problem: " + message);
    }
}