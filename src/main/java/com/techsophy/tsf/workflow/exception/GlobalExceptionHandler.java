package com.techsophy.tsf.workflow.exception;

import com.techsophy.tsf.workflow.model.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(ProcessIdNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleProcessException(ProcessIdNotFoundException ex, WebRequest request)
    {
        ApiErrorResponse errorDetails = new ApiErrorResponse(Instant.now(), ex.getMessage(),ex.errorCode,
                HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityIdNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> entityNotFoundException(EntityIdNotFoundException ex, WebRequest request)
    {
        ApiErrorResponse errorDetails = new ApiErrorResponse(Instant.now(), ex.getMessage(), ex.errorCode,
                HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserDetailsIdNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> userDetailsNotFoundException(UserDetailsIdNotFoundException ex, WebRequest request)
    {
        ApiErrorResponse errorDetails = new ApiErrorResponse(Instant.now(), ex.getMessage(), ex.errorCode,
                HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ApiErrorResponse> invalidInputException(InvalidInputException ex, WebRequest request)
    {
        ApiErrorResponse errorDetails = new ApiErrorResponse(Instant.now(), ex.getMessage(), ex.errorCode,
                HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(ExternalServiceErrorException.class)
    public ResponseEntity<ApiErrorResponse> externalServiceErrorException(ExternalServiceErrorException ex, WebRequest request)
    {
        ApiErrorResponse errorDetails = new ApiErrorResponse(Instant.now(), ex.getMessage(), ex.errorCode,
                HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
