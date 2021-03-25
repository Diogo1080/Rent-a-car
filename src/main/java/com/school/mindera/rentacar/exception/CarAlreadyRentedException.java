package com.school.mindera.rentacar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Car already rented exception trowed
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class CarAlreadyRentedException extends RuntimeException{
        public CarAlreadyRentedException(String message) { super(message); }
}
