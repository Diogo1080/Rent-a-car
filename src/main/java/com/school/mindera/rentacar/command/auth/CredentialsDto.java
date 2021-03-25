package com.school.mindera.rentacar.command.auth;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * CredentialsDto used for authentication purposes
 */
@Data
@Builder
public class CredentialsDto {
    @Email(message = "Your email doesn't fit")
    private String email;

    @NotBlank(message = "Must have a password")
    private String password;
}
