package com.msgsystems.jbugger.echipa2.backend.service;

import com.msgsystems.jbugger.echipa2.backend.service.ServiceOperationResultCode;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceOperationResultCodeTest {

    @Test
    public void testToHttpStatus() {
        // Test OK
        assertEquals(HttpStatus.OK, ServiceOperationResultCode.OK.toHttpStatus());

        // Test ERROR
        assertEquals(HttpStatus.BAD_REQUEST, ServiceOperationResultCode.ERROR.toHttpStatus());

        // Test NO_EFFECT
        assertEquals(HttpStatus.ACCEPTED, ServiceOperationResultCode.NO_EFFECT.toHttpStatus());

        // Test NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, ServiceOperationResultCode.NOT_FOUND.toHttpStatus());
    }
}