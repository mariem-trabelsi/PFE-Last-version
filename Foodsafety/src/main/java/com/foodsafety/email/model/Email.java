package com.foodsafety.email.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Email{
	
	private String to;
	private String subject;
	private String message;
	private String firstName;
    private String lastName;
    private String idToUpdate;

	public Email(String mailSubject, String mailMessage) {
		this.subject= mailSubject;
		this.message= mailMessage;
	}
	
}
