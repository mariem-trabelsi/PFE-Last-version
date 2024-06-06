package com.foodsafety.email.EmailSenderImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.foodsafety.email.EmailSender;

@Service
public class EmailSenderImpl implements EmailSender {
	
	@Autowired
	private final JavaMailSender mailSender;
	
	
	
	public EmailSenderImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void sendEmail(String to, String subject, String message) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom("stagiaires@archicompass.com");
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(message);
		this.mailSender.send(simpleMailMessage);
	}

}

