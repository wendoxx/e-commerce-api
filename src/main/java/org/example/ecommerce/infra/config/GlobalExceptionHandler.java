package org.example.ecommerce.infra.config;

import org.example.ecommerce.infra.config.exception.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public String handleOrderNotFoundException(OrderNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public String handleProductNotFoundException(ProductNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(OrderListIsEmptyException.class)
    public String handleOrderListIsEmptyException(OrderListIsEmptyException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ProductListIsEmptyException.class)
    public String handleProductListEmptyException(ProductListIsEmptyException e) {
        return e.getMessage();
    }

    @ExceptionHandler(UsernameIsNotAvailableException.class)
    public String handleUsernameIsNotAvailableException(UsernameIsNotAvailableException e) {
        return e.getMessage();
    }
}