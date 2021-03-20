package com.school.mindera.rentacar.controller;

import com.school.mindera.rentacar.enumerators.UserRole;
import com.school.mindera.rentacar.command.User.CreateUserDto;
import com.school.mindera.rentacar.command.User.UpdateUserDto;
import com.school.mindera.rentacar.command.User.UserDetailsDto;
import com.school.mindera.rentacar.service.UserServiceImp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

/**
 * User Controller provides end points for CRUD operations on users
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    private final UserServiceImp userService;

    /**
     * UserController constructor
     * @param userService Injects user Service for later use
     */
    public UserController(UserServiceImp userService) {
        this.userService = userService;
    }

    /**
     * Create new user
     * @param createUserDto {@link CreateUserDto}
     * @return {@link UserDetailsDto}
     */
    @PostMapping
    public ResponseEntity<UserDetailsDto> createUser(@Valid @RequestBody CreateUserDto createUserDto) {

        LOGGER.info("Request to create user - {} and role {}.", createUserDto, UserRole.CUSTOMER);
        UserDetailsDto userDetailsDto =  userService.addNewUser(createUserDto, UserRole.CUSTOMER);

        LOGGER.info("Retrieving created user {}", userDetailsDto);
        return new ResponseEntity<>(userDetailsDto, HttpStatus.CREATED);
    }

    /**
     * Get user with certain id
     * @param userId Receives user id
     * @return {@link UserDetailsDto}
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailsDto> getUserById(@PathVariable long userId) {
        LOGGER.info("Request to get user of id - {}", userId);
        UserDetailsDto userDetailsDto = userService.getUserById(userId);

        LOGGER.info("Retrieving user with info - {}",userDetailsDto);
        return new ResponseEntity<>(userDetailsDto, OK);
    }

    /**
     * Gets all users from database
     * @return {@link List<UserDetailsDto>}
     */
    @GetMapping
    public ResponseEntity<List<UserDetailsDto>> getAllUsers() {
        LOGGER.info("Retrieving all users");
        List<UserDetailsDto> usersList = userService.getAllUsers();

        LOGGER.info("Retrieving List of users with info - {}",usersList);
        return new ResponseEntity<>(usersList, OK);
    }

    /**
     * Update user with certain id
     * @param userId Receives user id
     * @param updateUserDto {@link UpdateUserDto}
     * @return {@link UserDetailsDto}
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDetailsDto> updateUser(@PathVariable long userId,
                                                     @Valid @RequestBody UpdateUserDto updateUserDto) {
        LOGGER.info("Request to update user of id - {} - with info - {}", userId,updateUserDto);
        UserDetailsDto userDetailsDto = userService.updateUser(userId, updateUserDto);

        LOGGER.info("Retrieving updated of user with info - {}",userDetailsDto);
        return new ResponseEntity<>(userDetailsDto, OK);
    }

    /**
     * Delete user with certain id
     * @param userId Receives user id
     * @return Ok if deleted
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable long userId) {
        LOGGER.info("Request to delete user of id - {}", userId);
        userService.deleteUser(userId);

        LOGGER.info("Responding if delete was successful or not");
        return new ResponseEntity(OK);
    }
}
