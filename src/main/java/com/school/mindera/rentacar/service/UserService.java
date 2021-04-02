package com.school.mindera.rentacar.service;

import com.school.mindera.rentacar.command.Paginated;
import com.school.mindera.rentacar.command.user.CreateUserDto;
import com.school.mindera.rentacar.command.user.UpdateUserDto;
import com.school.mindera.rentacar.command.user.UserDetailsDto;
import com.school.mindera.rentacar.enumerators.UserRole;
import com.school.mindera.rentacar.exception.DatabaseCommunicationException;
import com.school.mindera.rentacar.exception.UserAlreadyExistsException;
import com.school.mindera.rentacar.exception.UserNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Common interface for user service, provides methods to manage users
 */
public interface UserService {
    /**
     * Create new user
     * @param userRegistrationDto {@link CreateUserDto}
     * @param userRole {@link UserRole}
     * @return {@link UserDetailsDto}
     * @throws UserAlreadyExistsException when the user already exists
     * @throws DatabaseCommunicationException when database connection isn't established
     */
    UserDetailsDto addNewUser(CreateUserDto userRegistrationDto, UserRole userRole) throws UserAlreadyExistsException, DatabaseCommunicationException;

    /**
     * Get given user with id
     * @param userId Receives user id
     * @return {@link UserDetailsDto}
     * @throws UserNotFoundException when user is not found
     */
    UserDetailsDto getUserById(long userId) throws UserNotFoundException;


    /**
     * Gets list of users from database with pagination
     * @param pagination the page and number of elements per page
     * @return {@link Paginated<UserDetailsDto>}
     */
    Paginated<UserDetailsDto> getUsersList(Pageable pagination);

    /**
     * Deletes certain user with id
     * @param userId Receives user id
     * @throws UserNotFoundException when user is not found
     * @throws DatabaseCommunicationException when database connection isn't established
     */
    void deleteUser(long userId) throws UserNotFoundException, DatabaseCommunicationException;

    /**
     * Update user
     * @param userId Receives user is
     * @param updateUserDto Receives user info
     * @return {@link UserDetailsDto}
     * @throws UserNotFoundException when user is not found
     * @throws DatabaseCommunicationException when database connection isn't established
     */
    UserDetailsDto updateUser(long userId, UpdateUserDto updateUserDto) throws UserNotFoundException,DatabaseCommunicationException;
}
