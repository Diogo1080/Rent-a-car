package com.school.mindera.rentacar.exception;

/**
 * Invalid Rent Status exception trowed when Rent is found on an invalid state for a action
 */
public class InvalidRentStatusException extends RuntimeException {
    public InvalidRentStatusException(String message){
        super(message);
    }
}
