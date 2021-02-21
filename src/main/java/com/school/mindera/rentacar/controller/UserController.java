package com.school.mindera.rentacar.controller;

import com.school.mindera.rentacar.persistence.entity.UserEntity;
import com.school.mindera.rentacar.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity) {

        UserEntity createdUser = userService.createUser(userEntity);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
