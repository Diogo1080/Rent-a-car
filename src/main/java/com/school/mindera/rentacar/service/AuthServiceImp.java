package com.school.mindera.rentacar.service;

import com.school.mindera.rentacar.command.auth.CredentialsDto;
import com.school.mindera.rentacar.command.user.UserDetailsDto;
import com.school.mindera.rentacar.converter.UserConverter;
import com.school.mindera.rentacar.error.ErrorMessages;
import com.school.mindera.rentacar.exception.*;
import com.school.mindera.rentacar.persistence.entity.UserEntity;
import com.school.mindera.rentacar.persistence.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * An {@link AuthService} implementation
 */
@Service
public class AuthServiceImp implements AuthService{

    private final UserRepository userRepository;
    private static final Logger LOGGER = LogManager.getLogger(AuthServiceImp.class);

    public AuthServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @see AuthService#login(CredentialsDto)
     */
    @Override
    public UserDetailsDto login(CredentialsDto credentialsDto) throws WrongCredentialsException {
        // Verify if the credentials are correct
        LOGGER.debug("Getting user with credentials {}", credentialsDto);
        UserEntity userEntity = userRepository.findMatchingCredentials(
                credentialsDto.getEmail(),
                credentialsDto.getPassword()
            ).orElseThrow(() -> {
                LOGGER.error(ErrorMessages.WRONG_CREDENTIALS);
                return new WrongCredentialsException(ErrorMessages.WRONG_CREDENTIALS);
            });
        //Returning User details
        return UserConverter.fromUserEntityToUserDetailsDto(userEntity);
    }

}
