package com.msgsystems.jbugger.echipa2.backend.auth;

import com.msgsystems.jbugger.echipa2.backend.auth.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class JwtServiceTest {

    @Test
    public void testGenerateToken() {
        // Create instance of the service to test
        JwtService jwtService = new JwtService();

        // Create a UserDetails instance
        UserDetails userDetails = User.withUsername("testUser").password("password").authorities(new ArrayList<>()).build();

        // Generate a token
        String token = jwtService.generateToken(userDetails);

        // Verify the token
        assertNotNull(token);
        assertEquals("testUser", jwtService.extractUsername(token));
    }

    @Test
    public void testIsTokenValid() {
        // Create instance of the service to test
        JwtService jwtService = new JwtService();

        // Create a UserDetails instance
        UserDetails userDetails = User.withUsername("testUser").password("password").authorities(new ArrayList<>()).build();

        // Generate a token
        String token = jwtService.generateToken(userDetails);

        // Verify the token
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

}