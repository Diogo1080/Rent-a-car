package com.school.mindera.rentacar.error;

/**
 * Constant Error messages
 */
public class ErrorMessages {
    public static final String USER_NOT_FOUND = "Can't find any user with the given id";
    public static final String USER_ALREADY_EXISTS = "User with the given email already exists";

    public static final String CAR_NOT_FOUND = "Can't find any car with the given id";
    public static final String CAR_ALREADY_EXISTS = "Car with the given plate already exists";
    public static final String CAR_NOT_AVAILABLE = "Car is not available at the moment";

    public static final String RENT_NOT_FOUND = "Can't find any rent with the given id";
    public static final String CAR_ALREADY_DELIVERED = "Car already delivered to the customer";
    public static final String CAR_EXPECTED_TO_BE_UNAVAILABLE = "The car will be unavailable on the chosen dates";
    public static final String CAN_NOT_RETURN_CAR = "The current rent status doesn't allow you to return the car";
    public static final String CAN_NOT_DELETE_CAR_ALREADY_DELIVERED = "The rent cannot be delete because the car was already delivered";

    public static final String WRONG_CREDENTIALS = "The email doesn't exist or the password is wrong";

    public static final String DATABASE_COMMUNICATION_ERROR = "Database communication error";

    public static final String OPERATION_FAILED = "Failed to process the requested operation";

}
