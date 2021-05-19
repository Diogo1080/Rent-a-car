package com.school.mindera.rentacar.security;

import com.school.mindera.rentacar.command.auth.PrincipalDto;
import org.springframework.security.core.context.SecurityContextHolder;


public class AuthorizationValidatorService {
    public boolean hasRole(String role){
        return role.equals(getPrincipal().getUserRole().name());
    }

    public boolean isUser(long userId){
        return userId == getPrincipal().getUserId();
    }

    private PrincipalDto getPrincipal() {
        return (PrincipalDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
