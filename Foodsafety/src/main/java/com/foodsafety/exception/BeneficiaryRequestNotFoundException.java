package com.foodsafety.exception;

public class BeneficiaryRequestNotFoundException extends RuntimeException {
    public BeneficiaryRequestNotFoundException(String message) {
        super(message);
    }
}

