package com.foodsafety.exception;

public class CampaignRequestNotFoundException  extends RuntimeException {
    public CampaignRequestNotFoundException(String message) {
        super(message);
    }
}
