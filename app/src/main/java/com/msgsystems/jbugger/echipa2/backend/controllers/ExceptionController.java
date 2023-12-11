package com.msgsystems.jbugger.echipa2.backend.controllers;

import com.msgsystems.jbugger.echipa2.backend.service.ServiceOperationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = { ServiceOperationException.class })
    protected ResponseEntity<Object> handleServiceOperationException(ServiceOperationException ex) {
        System.out.println("ServiceEx");
        return new ResponseEntity<>(ex.getMessage(), ex.getResultCode().toHttpStatus());
    }



}
