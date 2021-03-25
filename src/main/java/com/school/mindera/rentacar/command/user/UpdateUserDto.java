package com.school.mindera.rentacar.command.user;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


/**
 * UpdateRentDto mainly used to store user info when updating users
 */
@Data
@Builder
public class UpdateUserDto {

    @NotNull(message="Your name can't be null")
    private String firstName;


    private String lastName;


    private String licenseId;

    @Email(message="email not valid")
    private String email;
}
