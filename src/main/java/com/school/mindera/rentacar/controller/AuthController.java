package com.school.mindera.rentacar.controller;

import com.school.mindera.rentacar.command.auth.CredentialsDto;
import com.school.mindera.rentacar.command.auth.LoggedInDto;
import com.school.mindera.rentacar.command.auth.PrincipalDto;
import com.school.mindera.rentacar.error.ErrorMessages;
import com.school.mindera.rentacar.exception.RentacarApiException;
import com.school.mindera.rentacar.service.AuthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller responsible for authentication operations
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger LOGGER = LogManager.getLogger(AuthController.class);

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Login user with email and password
     *
     * @param credentials
     * @return {@link LoggedInDto} with user info and jwt token
     */
    @PostMapping("/login")
    public ResponseEntity<LoggedInDto> login(@RequestBody CredentialsDto credentials) {
        LOGGER.info("Request to login user with email {}", credentials.getEmail());

        LoggedInDto loggedIn;
        try {
            loggedIn = authService.loginUser(credentials);
            ResponseCookie responseCookie = ResponseCookie
                    .from("auth_by_cookie", loggedIn.getToken())
                    .maxAge(24 * 60 * 60)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .build();

            LOGGER.info("User logged in successfully. Retrieving jwt token and setting cookie");

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .body(loggedIn);

        } catch (RentacarApiException e) {
            // Since RentacarApiException exceptions are thrown by us, we just throw them
            throw e;

        } catch (Exception e) {
            // With all others exceptions we log them and throw a generic exception
            LOGGER.error("Failed to loging user - {}", credentials, e);
            throw new RentacarApiException(ErrorMessages.OPERATION_FAILED, e);
        }

    }

    public ResponseEntity<Void> logout(@AuthenticationPrincipal PrincipalDto principal ) {
        LOGGER.info("Request to logOut user with id {}", principal.getUserId());
        try {
            ResponseCookie responseCookie = ResponseCookie
                    .from("auth_by_cookie", null)
                    .httpOnly(true)
                    .secure(false)
                    .maxAge(0)
                    .path("/")
                    .build();

            LOGGER.info("Successful logout.");

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .build();

        }catch(Exception e){
            LOGGER.error("Failed while logging out user with exception {}",e);
            throw new RentacarApiException(ErrorMessages.OPERATION_FAILED,e);
        }
    }
}
