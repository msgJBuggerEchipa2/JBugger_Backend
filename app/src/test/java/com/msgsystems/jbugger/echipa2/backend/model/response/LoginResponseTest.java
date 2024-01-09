package com.msgsystems.jbugger.echipa2.backend.model.response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginResponseTest {

    @Test
    public void testLoginResponse() {
        // Create instance of LoginResponse
        LoginResponse loginResponse = new LoginResponse("user", "token");

        // Test the getUser method
        String user = loginResponse.getUser();
        assertEquals("user", user);

        // Test the getToken method
        String token = loginResponse.getToken();
        assertEquals("token", token);

        // Test the setUser method
        loginResponse.setUser("newUser");
        assertEquals("newUser", loginResponse.getUser());

        // Test the setToken method
        loginResponse.setToken("newToken");
        assertEquals("newToken", loginResponse.getToken());
    }
}