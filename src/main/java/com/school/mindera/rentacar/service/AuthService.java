package com.school.mindera.rentacar.service;

import com.school.mindera.rentacar.command.auth.CredentialsDto;
import com.school.mindera.rentacar.command.user.UserDetailsDto;
import com.school.mindera.rentacar.exception.WrongCredentialsException;

/**
 * Common interface for Authentication service, provides methods to manage Authentication
 */
public interface AuthService {


    /**
     * Checks if credentials given are correct
     * @param credentialsDto {@link CredentialsDto}
     * @return {@link UserDetailsDto}
     * @throws WrongCredentialsException when credentials are wrong
     */
    UserDetailsDto login (CredentialsDto credentialsDto) throws WrongCredentialsException;
}
