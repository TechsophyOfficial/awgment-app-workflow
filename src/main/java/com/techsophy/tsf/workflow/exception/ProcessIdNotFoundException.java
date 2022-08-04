package com.techsophy.tsf.workflow.exception;

public class ProcessIdNotFoundException extends RuntimeException
{
    final String errorCode;
    final String message;
    public ProcessIdNotFoundException(String errorCode,String message)
    {
        super(message);
        this.errorCode=errorCode;
        this.message=message;
    }
}
