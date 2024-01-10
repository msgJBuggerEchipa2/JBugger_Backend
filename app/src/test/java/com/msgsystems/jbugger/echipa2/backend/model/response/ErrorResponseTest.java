package com.msgsystems.jbugger.echipa2.backend.model.response;

import com.msgsystems.jbugger.echipa2.backend.model.response.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorResponseTest {

    @Test
    public void testErrorResponse() {
        // Create instance of ErrorResponse
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request");

        // Test the getHttpStatus method
        HttpStatus httpStatus = errorResponse.getHttpStatus();
        assertEquals(HttpStatus.BAD_REQUEST, httpStatus);

        // Test the getMessage method
        String message = errorResponse.getMessage();
        assertEquals("Bad Request", message);

        // Test the setHttpStatus method
        errorResponse.setHttpStatus(HttpStatus.NOT_FOUND);
        assertEquals(HttpStatus.NOT_FOUND, errorResponse.getHttpStatus());

        // Test the setMessage method
        errorResponse.setMessage("Not Found");
        assertEquals("Not Found", errorResponse.getMessage());
    }
}
