package org.example.ecommerce.infra.config.exception;

public class ProductListIsEmptyException extends RuntimeException {
    public ProductListIsEmptyException(String message) {
        super("Oh no! Something went wrong! Here is the problem: " + message);
    }
}
