package com.msgsystems.jbugger.echipa2.backend.service;

import com.msgsystems.jbugger.echipa2.backend.service.ServiceOperationResult;
import com.msgsystems.jbugger.echipa2.backend.service.ServiceOperationException;
import com.msgsystems.jbugger.echipa2.backend.service.ServiceOperationResultCode;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceOperationResultTest {

    @Test
    public void testServiceOperationResult() {
        // Test Ok result
        ServiceOperationResult<String> okResult = ServiceOperationResult.Ok("OK");
        assertEquals("OK", okResult.getValue());
        assertEquals(ServiceOperationResultCode.OK, okResult.getResultCode());
        assertTrue(okResult.isSuccess());
        assertTrue(okResult.isOk());

        // Test Error result
        ServiceOperationResult<String> errorResult = ServiceOperationResult.Error("Error");
        assertEquals("Error", errorResult.getValue());
        assertEquals(ServiceOperationResultCode.ERROR, errorResult.getResultCode());
        assertFalse(errorResult.isSuccess());
        assertTrue(errorResult.isError());

        // Test NoEffect result
        ServiceOperationResult<String> noEffectResult = ServiceOperationResult.NoEffect("No effect");
        assertEquals("No effect", noEffectResult.getValue());
        assertEquals(ServiceOperationResultCode.NO_EFFECT, noEffectResult.getResultCode());
        assertTrue(noEffectResult.isSuccess());
        assertTrue(noEffectResult.isNoEffect());

        // Test NotFound result
        ServiceOperationResult<String> notFoundResult = ServiceOperationResult.NotFound("Not found");
        assertEquals("Not found", notFoundResult.getValue());
        assertEquals(ServiceOperationResultCode.NOT_FOUND, notFoundResult.getResultCode());
        assertFalse(notFoundResult.isSuccess());
        assertTrue(notFoundResult.isNotFound());

        // Test getValueInterceptError method
        assertThrows(ServiceOperationException.class, notFoundResult::getValueInterceptError);

        // Test toResponseEntity method
        ResponseEntity<String> responseEntity = assertDoesNotThrow(okResult::toResponseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("OK", responseEntity.getBody());
    }
}