package com.school.mindera.rentacar.command.User;

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
    @NotBlank(message = "Must have a first name")
    private String firstName;

    @NotBlank(message = "Must have a last name")
    private String lastName;

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
