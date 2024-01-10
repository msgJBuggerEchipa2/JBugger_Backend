package com.msgsystems.jbugger.echipa2.backend.auth;

import com.msgsystems.jbugger.echipa2.backend.auth.NotPermittedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotPermittedExceptionTest {

    @Test
    public void testNotPermittedException() {
        // Create instance of the exception to test
        NotPermittedException notPermittedException = new NotPermittedException("Test message");

        // Verify the message
        assertEquals("Test message", notPermittedException.getMessage());
    }
}