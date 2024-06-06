package com.foodsafety.email.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.foodsafety.email.EmailSenderImpl.EmailSenderImpl;
import com.foodsafety.email.EmailSenderImpl.JwtService;
import com.foodsafety.email.model.Email;


@Component
@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class EmailController {
	
	@Autowired
	private final EmailSenderImpl emailSenderImpl ;

	@Autowired
	private final JwtService jwtService;
	
	@Value("${mail.subject}")
    private String mailSubject;
    
    @Value("${mail.message}")
    private String mailMessage;
	
  
	public EmailController(EmailSenderImpl emailSenderImpl, JwtService jwtService) {
		this.emailSenderImpl = emailSenderImpl;
		this.jwtService = jwtService;
	}
	
	@GetMapping("/settings")
    public ResponseEntity<Email> getMailSettings() {
        Email settings = new Email(mailSubject, mailMessage);
        return ResponseEntity.ok(settings);
    }

	
	

	@PostMapping(value = "/mail")
	ResponseEntity<String> sendMail(@RequestBody Email email) {        
	    try {
	        // Créez une carte pour stocker les valeurs de remplacement
	        Map<String, String> replacements = new HashMap<>();
	        replacements.put("firstName", email.getFirstName());
	        replacements.put("lastName", email.getLastName());
			String uuid = email.getIdToUpdate();

			String publicVariableLink = "http://localhost:4200/loading/"; // Base link

			if (uuid != null) {
				uuid = jwtService.generateJwtToken(uuid);
				publicVariableLink += uuid;
			}

			replacements.put("publicVariableLink", publicVariableLink);

			// Effectuer les remplacements dans le message
	        String message = replacePlaceholders(mailMessage, replacements);

	        // Envoyez l'e-mail avec le sujet et le message mis à jour
	        this.emailSenderImpl.sendEmail(email.getTo(), mailSubject, message);
	        return ResponseEntity.status(200).build();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(500).build();
	    }
	}

	// Méthode utilitaire pour remplacer les placeholders dans la chaîne avec les valeurs correspondantes
	private String replacePlaceholders(String input, Map<String, String> replacements) {
	    for (Map.Entry<String, String> entry : replacements.entrySet()) {
	        String placeholder = "{" + entry.getKey() + "}";
	        String replacement = entry.getValue();
	        input = input.replace(placeholder, replacement);
	    }
	    return input;
	}

	



	

}