package org.example.ecommerce.infra.config.exception;

public class OrderListIsEmptyException extends RuntimeException {
    public OrderListIsEmptyException(String message) {
        super("Oh no! Something went wrong! Here is the problem: " + message);
    }
}
