package com.foodsafety.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

	@Component
	@Profile("PROD")
	public class ProdBean {
		public ProdBean() {
			System.out.println("---PROD PROD.DEVBEAN()");
		}
}

