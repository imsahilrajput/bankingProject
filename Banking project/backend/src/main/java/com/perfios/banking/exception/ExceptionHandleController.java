package com.perfios.banking.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandleController {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(final Exception exception, final HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        response.setErrorCode(HttpStatus.NOT_FOUND.value());
        response.setPath(request.getRequestURI());
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<ExceptionResponse> handleEntityExistsException(final Exception exception, final HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        response.setErrorCode(HttpStatus.CONFLICT.value());
        response.setPath(request.getRequestURI());
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handleIllegalStateException(final Exception exception, final HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        response.setErrorCode(HttpStatus.BAD_REQUEST.value());
        response.setPath(request.getRequestURI());
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionResponse> handleException(final Exception exception, final HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        System.out.println(exception.getMessage());
        exceptionResponse.setMessage(exception.getMessage());
        exceptionResponse.setPath(request.getRequestURI());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionResponse> handleNullPointerException(final Exception exception, final HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        System.out.println(exception.getMessage());
        exceptionResponse.setMessage(exception.getMessage());
        exceptionResponse.setPath(request.getRequestURI());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
