package com.msgsystems.jbugger.echipa2.backend.auth;

import com.msgsystems.jbugger.echipa2.backend.auth.RegisterUserDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterUserDtoTest {

    @Test
    public void testRegisterUserDto() {
        // Create instance of the DTO to test
        RegisterUserDto registerUserDto = new RegisterUserDto();

        // Set values
        registerUserDto.setUsername("testUser");
        registerUserDto.setPassword("testPassword");
        registerUserDto.setFullName("Test User");

        // Verify the values
        assertEquals("testUser", registerUserDto.getUsername());
        assertEquals("testPassword", registerUserDto.getPassword());
        assertEquals("Test User", registerUserDto.getFullName());
    }
}
