package com.school.mindera.rentacar.exception;

/**
 * Wrong credentials exception is trowed when the credentials given by user aren't matched with any from database
 */
public class WrongCredentialsException extends RuntimeException {
    public WrongCredentialsException(String message) {
        super(message);
    }
}
