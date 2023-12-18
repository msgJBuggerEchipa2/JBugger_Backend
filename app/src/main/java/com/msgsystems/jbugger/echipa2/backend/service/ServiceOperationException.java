package com.msgsystems.jbugger.echipa2.backend.service;

public class ServiceOperationException extends RuntimeException {
    private final ServiceOperationResultCode resultCode;

    public ServiceOperationException(ServiceOperationResultCode resultCode, String message) {
        super("["+resultCode.name()+"]: "+ message);
        this.resultCode = resultCode;
    }

    public ServiceOperationResultCode getResultCode() {
        return resultCode;
    }
}
