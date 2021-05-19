package com.school.mindera.rentacar.exception;

/**
 * Global rentacar exception
 */
public class RentacarApiException extends RuntimeException {
    public RentacarApiException() {
    }

    public RentacarApiException(String message) {
        super(message);
    }

    public RentacarApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public RentacarApiException(Throwable cause) {
        super(cause);
    }

    public RentacarApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
