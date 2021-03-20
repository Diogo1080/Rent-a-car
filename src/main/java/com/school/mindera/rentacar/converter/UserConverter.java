package com.school.mindera.rentacar.converter;

import com.school.mindera.rentacar.command.User.CreateUserDto;
import com.school.mindera.rentacar.command.User.UserDetailsDto;
import com.school.mindera.rentacar.persistence.entity.UserEntity;

/**
 * Rent converter
 * Transforms from one data model to another
 */
public class UserConverter {

    /**
     * From {@link CreateUserDto} to {@link UserEntity}
     * @param createUserDto {@link CreateUserDto}
     * @return {@link UserEntity}
     */
    public static UserEntity fromCreateUserDtoToUserEntity(CreateUserDto createUserDto) {
        return UserEntity.builder()
                .firstName(createUserDto.getFirstName())
                .lastName(createUserDto.getLastName())
                .email(createUserDto.getEmail())
                .licenseId(createUserDto.getLicenseId())
                .password(createUserDto.getPassword())
                .build();
    }

    /**
     * From {@link UserEntity} to {@link UserDetailsDto}
     * @param userEntity {@link UserEntity}
     * @return {@link UserDetailsDto}
     */
    public static UserDetailsDto fromUserEntityToUserDetailsDto(UserEntity userEntity) {
        return UserDetailsDto.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .licenseId(userEntity.getLicenseId())
                .build();
    }

}
