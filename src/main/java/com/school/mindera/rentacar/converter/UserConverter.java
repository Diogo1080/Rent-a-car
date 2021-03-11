package com.school.mindera.rentacar.converter;

import com.school.mindera.rentacar.model.User.CreateUserDto;
import com.school.mindera.rentacar.model.User.UserDetailsDto;
import com.school.mindera.rentacar.persistence.entity.UserEntity;

public class UserConverter {

    public static UserEntity fromCreateUserDtoToUserEntity(CreateUserDto createUserDto) {
        return UserEntity.builder()
                .firstName(createUserDto.getFirstName())
                .lastName(createUserDto.getLastName())
                .email(createUserDto.getEmail())
                .licenseId(createUserDto.getLicenseId())
                .password(createUserDto.getPassword())
                .build();
    }

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
