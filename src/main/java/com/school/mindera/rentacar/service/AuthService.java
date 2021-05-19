package com.school.mindera.rentacar.service;

import com.school.mindera.rentacar.command.auth.CredentialsDto;
import com.school.mindera.rentacar.command.auth.LoggedInDto;
import com.school.mindera.rentacar.command.auth.PrincipalDto;
import com.school.mindera.rentacar.command.user.UserDetailsDto;

/**
 * Common interface for authorization operations
 */
public interface AuthService {

    /**
     * Login user
     * @param credentialsDto
     * @return {@link LoggedInDto} logged in user details
     */
    LoggedInDto loginUser(CredentialsDto credentialsDto);

    /**
     * Validate token
     * @param token
     * @return {@link PrincipalDto} principal authenticated
     */
    PrincipalDto validateToken(String token);
}
