package com.school.mindera.rentacar.exception;

public class DatabaseCommunicationException extends RuntimeException {
    public DatabaseCommunicationException(String message){
        super(message);
    }
    public DatabaseCommunicationException(String message, Throwable e){
        super(message, e);
    }
}
