package com.foodsafety.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
@Component
@Profile("TEST")
public class TestBean {
	public TestBean() {
   System.out.println("---TEST TEST.TESTBEAN()");
		}
}
