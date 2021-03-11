package com.school.mindera.rentacar.model.User;

import com.school.mindera.rentacar.enumerators.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetailsDto {


    private long id;


    private String firstName;


    private String lastName;


    private String licenseId;


    private String email;
}
