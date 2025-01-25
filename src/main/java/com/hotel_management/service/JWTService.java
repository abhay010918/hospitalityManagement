package com.hotel_management.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry.duration}")
    private long expiryTime; // Changed from String to long

    private Algorithm algorithm;

    @PostConstruct
    public void PostConstruct() {
        algorithm = Algorithm.HMAC256(algorithmKey); // Initialize the algorithm
    }

    public String generateToken(String username) {
        return JWT.create()
                .withClaim("name", username) // Add custom claims
                .withExpiresAt(new Date(System.currentTimeMillis() + expiryTime)) // Set expiration time
                .withIssuer(issuer) // Add the issuer
                .sign(algorithm); // Sign the token with the algorithm
    }

    public String getUsername(String token){
        DecodedJWT verify = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return verify.getClaim("name").asString();
    }
}
