package com.msgsystems.jbugger.echipa2.backend.controllers;

import com.msgsystems.jbugger.echipa2.backend.auth.DeactivatedUserException;
import com.msgsystems.jbugger.echipa2.backend.auth.NotPermittedException;
import com.msgsystems.jbugger.echipa2.backend.service.ServiceOperationException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = { ServiceOperationException.class })
    protected ResponseEntity<Object> handleException(ServiceOperationException ex) {
        System.out.println("ServiceEx");
        return new ResponseEntity<>(ex.getMessage(), ex.getResultCode().toHttpStatus());
    }

    @ExceptionHandler(value = { NotPermittedException.class })
    protected ResponseEntity<Object> handleException(NotPermittedException ex) {
        System.out.println("NotPermittedEx");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = { DeactivatedUserException.class })
    protected ResponseEntity<Object> handleException(DeactivatedUserException ex) {
        System.out.println("DeactivatedUserException");
        return new ResponseEntity<>("User account is suspended.", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = { SignatureException.class })
    protected ResponseEntity<Object> handleException(SignatureException ex) {
        System.out.println("SignatureException");
        return new ResponseEntity<>("Signature check failed.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = { MalformedJwtException.class })
    protected ResponseEntity<Object> handleException(MalformedJwtException ex) {
        System.out.println("MalformedJwtException");
        return new ResponseEntity<>("Invalid JWT.", HttpStatus.UNAUTHORIZED);
    }
}
