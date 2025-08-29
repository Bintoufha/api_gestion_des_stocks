package com.bintoufha.gestionStocks.handlers;


import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(EntityNoFoundException.class)
    public ResponseEntity<ErrorDto> handleException(EntityNoFoundException exception , WebRequest webRequest){

        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        final ErrorDto errorDto = ErrorDto.builder()
                .codes(exception.getErrorCodes())
                .httpCode(notFound.value())
                .message(exception.getMessage())
                .build();
        return new  ResponseEntity<>(errorDto,notFound);
    }

    @ExceptionHandler(InvalEntityException.class)
    public ResponseEntity<ErrorDto> handleException(InvalEntityException exception, WebRequest webRequest) {

        final HttpStatus notFound = HttpStatus.BAD_REQUEST;
        final ErrorDto errorDto = ErrorDto.builder()
                .codes(exception.getErrorCodes())
                .httpCode(notFound.value())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorDto, notFound);
    }

//    @ExceptionHandler(InvalidEntityException.class)
//    public ResponseEntity<ErrorDto> handleException(InvalidEntityException exception, WebRequest webRequest) {
//        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
//
//        final ErrorDto errorDto = ErrorDto.builder()
//                .code(exception.getErrorCode())
//                .httpCode(badRequest.value())
//                .message(exception.getMessage())
//                .errors(exception.getErrors())
//                .build();
//
//        return new ResponseEntity<>(errorDto, badRequest);
//    }
//
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleException(BadCredentialsException exception, WebRequest webRequest) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        final ErrorDto errorDto = ErrorDto.builder()
                .codes(ErrorCodes.BAD_CREDENTIALS)
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .errors(Collections.singletonList("Login et / ou mot de passe incorrecte"))
                .build();

        return new ResponseEntity<>(errorDto, badRequest);
    }

}
