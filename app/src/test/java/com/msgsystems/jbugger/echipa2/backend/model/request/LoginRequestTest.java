package com.msgsystems.jbugger.echipa2.backend.model.request;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginRequestTest {

    @Test
    public void testLoginRequest() {
        // Create instance of LoginRequest
        LoginRequest loginRequest = new LoginRequest("username", "password");

        // Test the getUsername method
        String username = loginRequest.getUsername();
        assertEquals("username", username);

        // Test the getPassword method
        String password = loginRequest.getPassword();
        assertEquals("password", password);

        // Test the setUsername method
        loginRequest.setUsername("newUsername");
        assertEquals("newUsername", loginRequest.getUsername());

        // Test the setPassword method
        loginRequest.setPassword("newPassword");
        assertEquals("newPassword", loginRequest.getPassword());
    }
}