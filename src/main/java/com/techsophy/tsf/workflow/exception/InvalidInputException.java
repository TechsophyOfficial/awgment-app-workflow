package com.techsophy.tsf.workflow.exception;

public class InvalidInputException extends RuntimeException
{
    final String errorCode;
    final String message;
    public InvalidInputException(String errorCode,String message)
    {
        super(message);
        this.errorCode=errorCode;
        this.message=message;
    }
}
