package com.foodsafety.exception;

public class FundsNotFoundException extends RuntimeException {
    public FundsNotFoundException(String message) {
        super(message);
    }
}
