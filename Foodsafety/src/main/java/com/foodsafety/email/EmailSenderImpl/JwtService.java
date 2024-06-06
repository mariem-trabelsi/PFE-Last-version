package com.foodsafety.email.EmailSenderImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Base64;


@Service
public class JwtService {


    private final String secretKey;

    public String generateJwtToken(String inputData) {
        return Jwts.builder()
                .setSubject(inputData)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public JwtService() {this.secretKey="NydrqGjDFLAz4qsh47qJLv5kb/y6+P3u9qqDRzbSIHS2IZ5D52M42ZcnAXTtfawP";}
}

