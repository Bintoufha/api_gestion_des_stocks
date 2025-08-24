package com.bintoufha.gestionStocks.exception;

import lombok.Getter;

import java.util.List;

public class InvalEntityException extends RuntimeException{
    @Getter
    private ErrorCodes errorCodes;
    @Getter
    private List<String> errors;

    public InvalEntityException(String message){
        super(message);
    }

    public InvalEntityException(String message, Throwable cause){
        super(message,cause);

    }

    public InvalEntityException(String message, Throwable cause, ErrorCodes errorCodes){
        super(message,cause);
        this.errorCodes = errorCodes;
    }

    public InvalEntityException(String message, ErrorCodes errorCodes, List<String>errors){
        super(message);
        this.errorCodes = errorCodes;
        this.errors = errors;
    }
}
