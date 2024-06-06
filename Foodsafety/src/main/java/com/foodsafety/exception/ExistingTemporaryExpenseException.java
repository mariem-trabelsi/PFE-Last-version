package com.foodsafety.exception;

public class ExistingTemporaryExpenseException  extends RuntimeException {
    public ExistingTemporaryExpenseException(String message) {
        super(message);
    }
}
