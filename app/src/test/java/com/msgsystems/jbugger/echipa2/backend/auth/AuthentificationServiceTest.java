package com.msgsystems.jbugger.echipa2.backend.auth;

import com.msgsystems.jbugger.echipa2.backend.auth.AuthenticationService;
import com.msgsystems.jbugger.echipa2.backend.auth.LoginUserDto;
import com.msgsystems.jbugger.echipa2.backend.auth.RegisterUserDto;
import com.msgsystems.jbugger.echipa2.backend.domain.User;
import com.msgsystems.jbugger.echipa2.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthentificationServiceTest {

    @Test
    public void testSignup() {
        // Create mocks
        UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
        PasswordEncoder passwordEncoderMock = Mockito.mock(PasswordEncoder.class);
        AuthenticationManager authenticationManagerMock = Mockito.mock(AuthenticationManager.class);

        // Create instance of the service to test
        AuthenticationService authService = new AuthenticationService(userRepositoryMock, authenticationManagerMock, passwordEncoderMock);

        // Define the behavior of the mocks
        when(passwordEncoderMock.encode(anyString())).thenReturn("encodedPassword");
        when(userRepositoryMock.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        // Use the service to test the method
        RegisterUserDto input = new RegisterUserDto();
        input.setUsername("testUser");
        input.setPassword("testPassword");

        User result = authService.signup(input);

        // Verify the result
        assertEquals("testUser", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
    }

    @Test
    public void testAuthenticate() {
        // Create mocks
        UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
        PasswordEncoder passwordEncoderMock = Mockito.mock(PasswordEncoder.class);
        AuthenticationManager authenticationManagerMock = Mockito.mock(AuthenticationManager.class);

        // Create instance of the service to test
        AuthenticationService authService = new AuthenticationService(userRepositoryMock, authenticationManagerMock, passwordEncoderMock);

        // Define the behavior of the mock
        when(authenticationManagerMock.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);

        // Use the service to test the method
        LoginUserDto input = new LoginUserDto();
        input.setUsername("testUser");
        input.setPassword("testPassword");

        User result = authService.authenticate(input);

        // Verify the result
        assertNull(result);
    }
}