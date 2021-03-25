package com.school.mindera.rentacar.controller;

import com.school.mindera.rentacar.command.auth.CredentialsDto;
import com.school.mindera.rentacar.command.user.UserDetailsDto;
import com.school.mindera.rentacar.service.AuthServiceImp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Auth Controller provides end points for authentication purposes
 */
@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthServiceImp authService;
    private static Logger LOGGER = LogManager.getLogger(CarController.class);

    public AuthController(AuthServiceImp authService) {
        this.authService = authService;
    }

    /**
     * Login end Point
     * @param credentialsDto {@link CredentialsDto}
     * @return {@link UserDetailsDto}
     */
    @PostMapping("/login")
    public ResponseEntity<UserDetailsDto> Login (@Valid @RequestBody CredentialsDto credentialsDto){
        LOGGER.info("Request to login with credentials - {}", credentialsDto);
        UserDetailsDto userDetails = authService.login(credentialsDto);

        LOGGER.info("Retrieving logged in user - {}", userDetails);
        return new ResponseEntity<>(userDetails, HttpStatus.CREATED);
    }

}
