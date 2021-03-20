package com.school.mindera.rentacar.exception;

public class InvalidRentStatusException extends RuntimeException {
    public InvalidRentStatusException(String message){
        super(message);
    }
}
