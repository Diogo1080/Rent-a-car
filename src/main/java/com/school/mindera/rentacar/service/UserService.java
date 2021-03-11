package com.school.mindera.rentacar.service;

import com.school.mindera.rentacar.converter.UserConverter;
import com.school.mindera.rentacar.enumerators.UserRole;
import com.school.mindera.rentacar.error.ErrorMessages;
import com.school.mindera.rentacar.exception.UserAlreadyExistsException;
import com.school.mindera.rentacar.exception.UserNotFoundException;
import com.school.mindera.rentacar.model.User.CreateUserDto;
import com.school.mindera.rentacar.model.User.UpdateUserDto;
import com.school.mindera.rentacar.model.User.UserDetailsDto;
import com.school.mindera.rentacar.persistence.entity.UserEntity;
import com.school.mindera.rentacar.persistence.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetailsDto createUser(CreateUserDto userRegistrationDto, UserRole userRole) {

        // Build UserEntity
        UserEntity userEntity = UserConverter.fromCreateUserDtoToUserEntity(userRegistrationDto);
        userEntity.setRole(userRole);

        // Persist user into database
        try {
            userRepository.save(userEntity);
        } catch (DataIntegrityViolationException sqlException) {
            throw new UserAlreadyExistsException(ErrorMessages.USER_ALREADY_EXISTS);
        }

        // Build UserDetailsDto to return to the client
        return UserConverter.fromUserEntityToUserDetailsDto(userEntity);
    }

    public UserDetailsDto getUserById(long userId) {

        // Get user details from database
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));

        // Build UserDetailsDto to return to the client
        return UserConverter.fromUserEntityToUserDetailsDto(userEntity);
    }

    public List<UserDetailsDto> getAllUsers() {

        // Get all users from database
        Iterable<UserEntity> usersList = userRepository.findAll();

        // Convert list items from UserEntity to UserDetailsDto
        List<UserDetailsDto> usersListResponse = new ArrayList<>();
        for (UserEntity user : usersList) {
            usersListResponse.add(UserConverter.fromUserEntityToUserDetailsDto(user));
        }

        return usersListResponse;
    }

    public void deleteUser(long userId) {

        // Verify if the user exists
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));

        // Delete user
        userRepository.delete(user);
    }

    public UserDetailsDto updateUser(long userId, UpdateUserDto updateUserDto) {

        // Verify if the user exists
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));

        // Update data with userDetails received
        userEntity.setFirstName(updateUserDto.getFirstName());
        userEntity.setLastName(updateUserDto.getLastName());
        userEntity.setEmail(updateUserDto.getEmail());
        userEntity.setLicenseId(updateUserDto.getLicenseId());

        // Save changes
        userRepository.save(userEntity);

        return UserConverter.fromUserEntityToUserDetailsDto(userEntity);
    }
}
