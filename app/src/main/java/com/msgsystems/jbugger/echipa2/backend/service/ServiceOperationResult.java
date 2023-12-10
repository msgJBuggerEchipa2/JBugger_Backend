package com.msgsystems.jbugger.echipa2.backend.service;

import org.springframework.http.ResponseEntity;

public class ServiceOperationResult<T> {
    private final T value;
    private String message;
    private ServiceOperationResultCode resultCode;

    private Boolean success =false;

    public static <X> ServiceOperationResult<X> Ok(X value){
        return new ServiceOperationResult<>(value)
                .setResultCode(ServiceOperationResultCode.OK)
                .setMessage("OK")
                .setSuccess(true);
    }
    public static <X> ServiceOperationResult<X> Error(X value){
        return new ServiceOperationResult<>(value)
                .setResultCode(ServiceOperationResultCode.ERROR)
                .setMessage("Error")
                .setSuccess(false);
    }
    public static <X> ServiceOperationResult<X> NoEffect(X value){
        return new ServiceOperationResult<>(value)
                .setResultCode(ServiceOperationResultCode.NO_EFFECT)
                .setMessage("No effect")
                .setSuccess(true);
    }

    public static <X> ServiceOperationResult<X> NotFound(X value){
        return new ServiceOperationResult<>(value)
                .setResultCode(ServiceOperationResultCode.NOT_FOUND)
                .setMessage("Not found")
                .setSuccess(false);
    }



    public ServiceOperationResult(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public T getValueInterceptError() throws ServiceOperationException {
        if(!isSuccess()){
            throw new ServiceOperationException(getResultCode(), getMessage());
        }
        return value;
    }

    public String getMessage() {
        return message;
    }

    public ServiceOperationResultCode getResultCode() {
        return resultCode;
    }

    public ServiceOperationResult<T> setResultCode(ServiceOperationResultCode resultCode) {
        this.resultCode = resultCode;
        return this;
    }

    public ServiceOperationResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public Boolean isSuccess() {
        return success;
    }

    public ServiceOperationResult<T> setSuccess(Boolean success){
        this.success = success;
        return this;
    }

    public ResponseEntity<T> toResponseEntity() throws ServiceOperationException {
        return new ResponseEntity<T>(getValueInterceptError(), getResultCode().toHttpStatus());
    }

    public Boolean isOk() { return getResultCode() == ServiceOperationResultCode.OK; }
    public Boolean isError() { return getResultCode() == ServiceOperationResultCode.ERROR; }
    public Boolean isNoEffect() { return getResultCode() == ServiceOperationResultCode.NO_EFFECT; }
    public Boolean isNotFound() { return getResultCode() == ServiceOperationResultCode.NOT_FOUND; }
}
