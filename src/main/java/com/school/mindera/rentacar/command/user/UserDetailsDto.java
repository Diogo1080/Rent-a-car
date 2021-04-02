package com.school.mindera.rentacar.command.user;

import lombok.Builder;
import lombok.Data;

/**
 * UserDetailsDto mainly used to respond with user details
 */

@Data
@Builder
public class UserDetailsDto {


    private long id;


    private String firstName;


    private String lastName;


    private String licenseId;


    private String email;


}
