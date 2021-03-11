package com.school.mindera.rentacar.model.User;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class CreateUserDto {
    @NotNull(message="Name can't be null")
    private String firstName;


    private String lastName;


    private String licenseId;

    @Email(message = "Your email doesn't fit")
    private String email;


    private String password;
}
