package com.msgsystems.jbugger.echipa2.backend.service;

import org.springframework.http.HttpStatus;

public enum ServiceOperationResultCode {
    OK,
    ERROR,
    NO_EFFECT,
    NOT_FOUND;

    public HttpStatus toHttpStatus(){
        return switch (this){
            case OK -> HttpStatus.OK;
            case ERROR -> HttpStatus.BAD_REQUEST;
            case NO_EFFECT -> HttpStatus.ACCEPTED;
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
