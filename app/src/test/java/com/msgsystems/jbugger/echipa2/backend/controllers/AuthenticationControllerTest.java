package com.msgsystems.jbugger.echipa2.backend.controllers;

import com.msgsystems.jbugger.echipa2.backend.auth.*;
import com.msgsystems.jbugger.echipa2.backend.domain.User;
import com.msgsystems.jbugger.echipa2.backend.model.response.LoginResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthenticationControllerTest {

    @Test
    public void testRegister() {
        // Create mocks
        JwtService jwtService = mock(JwtService.class);
        AuthenticationService authenticationService = mock(AuthenticationService.class);

        // Create instance of the controller to test
        AuthenticationController authenticationController = new AuthenticationController(jwtService,
                authenticationService);

        // Mock the behavior of the signup method
        RegisterUserDto registerUserDto = new RegisterUserDto();
        User user = new User();
        when(authenticationService.signup(registerUserDto)).thenReturn(user);

        // Test the register method
        ResponseEntity<User> response = authenticationController.register(registerUserDto);

        // Verify the result
        assertEquals(user, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testAuthenticate() {
        // Create mocks
        JwtService jwtService = mock(JwtService.class);
        AuthenticationService authenticationService = mock(AuthenticationService.class);

        // Create instance of the controller to test
        AuthenticationController authenticationController = new AuthenticationController(jwtService,
                authenticationService);

        // Mock the behavior of the authenticate and generateToken methods
        LoginUserDto loginUserDto = new LoginUserDto();
        User user = new User();
        String token = "token";
        when(authenticationService.authenticate(loginUserDto)).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn(token);

        // Test the authenticate method
        ResponseEntity<LoginResponse> response = authenticationController.authenticate(loginUserDto);

        // Verify the result
        assertEquals(token, response.getBody().getToken());
        assertEquals(200, response.getStatusCodeValue());
    }
}
