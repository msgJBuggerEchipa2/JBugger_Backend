package com.msgsystems.jbugger.echipa2.backend.service;

import com.msgsystems.jbugger.echipa2.backend.service.ServiceOperationException;
import com.msgsystems.jbugger.echipa2.backend.service.ServiceOperationResultCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceOperationExceptionTest {

    @Test
    public void testServiceOperationException() {
        // Create instance of ServiceOperationException
        ServiceOperationException exception = new ServiceOperationException(ServiceOperationResultCode.ERROR,
                "Test message");

        // Test the getResultCode method
        ServiceOperationResultCode resultCode = exception.getResultCode();
        assertEquals(ServiceOperationResultCode.ERROR, resultCode);

        // Test the getMessage method
        String message = exception.getMessage();
        assertEquals("[ERROR]: Test message", message);
    }
}