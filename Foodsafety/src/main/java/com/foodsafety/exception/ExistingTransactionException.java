package com.foodsafety.exception;

public class ExistingTransactionException extends RuntimeException {
    public ExistingTransactionException(String message) {
        super(message);
    }
}

