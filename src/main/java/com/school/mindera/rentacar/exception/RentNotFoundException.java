package com.school.mindera.rentacar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Rent not found exception trowed when rent is not found in database
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RentNotFoundException extends RuntimeException {
    public RentNotFoundException(String message) { super(message); }
}
