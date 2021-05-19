package com.school.mindera.rentacar.command.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * CreateUserDto mainly used to store user info when creating users
 */

@Data
@Builder
public class CreateUserDto {

    @Schema(example="Rui")
    @NotBlank(message = "Must have a first name")
    private String firstName;

    @Schema(example="Magalhães")
    @NotBlank(message = "Must have a last name")
    private String lastName;

    @Schema(example="123123123")
    @NotBlank(message = "Must have a licence ID")
    private String licenseId;

    @Email(message = "Your email doesn't fit")
    private String email;

    @NotBlank(message = "Must have a password")
    private String password;


    /**
     * toString method
     * @return Returns a overwrite of toString in order to not log sensible data
     */
    @Override
    public String toString() {
        return "CreateUserDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", licenseId='" + licenseId + '\'' +
                ", email='" + email + '\'' +
                ", password='***'"+'\'' +
                '}';
    }
}
