package com.msgsystems.jbugger.echipa2.backend.controllers;

import com.msgsystems.jbugger.echipa2.backend.auth.NotPermittedException;
import com.msgsystems.jbugger.echipa2.backend.controllers.ExceptionController;
import com.msgsystems.jbugger.echipa2.backend.service.ServiceOperationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ExceptionControllerTest {

    @Test
    public void testHandleServiceOperationException() {
        // Create mock
        ServiceOperationException ex = mock(ServiceOperationException.class);

        // Mock the behavior of the exception
        when(ex.getMessage()).thenReturn("Service operation exception");
        when(ex.getResultCode().toHttpStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);

        // Create instance of the controller to test
        ExceptionController exceptionController = new ExceptionController();

        // Test the handleException method
        ResponseEntity<Object> response = exceptionController.handleException(ex);

        // Verify the result
        assertEquals("Service operation exception", response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testHandleNotPermittedException() {
        // Create mock
        NotPermittedException ex = mock(NotPermittedException.class);

        // Mock the behavior of the exception
        when(ex.getMessage()).thenReturn("Not permitted exception");

        // Create instance of the controller to test
        ExceptionController exceptionController = new ExceptionController();

        // Test the handleException method
        ResponseEntity<Object> response = exceptionController.handleException(ex);

        // Verify the result
        assertEquals("Not permitted exception", response.getBody());
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}