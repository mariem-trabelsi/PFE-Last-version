package com.foodsafety.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("default")

public class DevBean {
	public DevBean() {
		System.out.println("---DEV DEV.DEVBEAN()");
	}

}
