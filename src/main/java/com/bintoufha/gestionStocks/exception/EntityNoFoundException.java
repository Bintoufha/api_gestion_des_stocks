package com.bintoufha.gestionStocks.exception;


import lombok.Getter;

public class EntityNoFoundException extends RuntimeException {
    @Getter
    private ErrorCodes errorCodes;

    public EntityNoFoundException(String message) {
        super(message);
    }

    public  EntityNoFoundException (String message, Throwable cause){
        super(message, cause);
    }

    public  EntityNoFoundException (String message, Throwable cause, ErrorCodes errorCodes){
        super(message, cause);
        this.errorCodes = errorCodes;
    }

    public  EntityNoFoundException (String message, ErrorCodes errorCodes){
        super(message);
        this.errorCodes = errorCodes;
    }
}
