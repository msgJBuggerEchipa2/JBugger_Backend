package com.msgsystems.jbugger.echipa2.backend.auth;

import com.msgsystems.jbugger.echipa2.backend.auth.LoginUserDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginUserDtoTest {

    @Test
    public void testLoginUserDto() {
        // Create instance of the DTO to test
        LoginUserDto loginUserDto = new LoginUserDto();

        // Set values
        loginUserDto.setUsername("testUser");
        loginUserDto.setPassword("testPassword");

        // Verify the values
        assertEquals("testUser", loginUserDto.getUsername());
        assertEquals("testPassword", loginUserDto.getPassword());

        // Verify the toString method
        String expectedToString = "LoginUserDto{username='testUser', password='testPassword'}";
        assertEquals(expectedToString, loginUserDto.toString());
    }
}