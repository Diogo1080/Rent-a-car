package com.school.mindera.rentacar.controller;

import com.school.mindera.rentacar.enumerators.UserRole;
import com.school.mindera.rentacar.model.User.CreateUserDto;
import com.school.mindera.rentacar.model.User.UpdateUserDto;
import com.school.mindera.rentacar.model.User.UserDetailsDto;
import com.school.mindera.rentacar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping
    public ResponseEntity<UserDetailsDto> createUser(@Valid @RequestBody CreateUserDto createUserDto) {

        UserDetailsDto userDetailsDto =  userService.createUser(createUserDto, UserRole.CUSTOMER);

        return new ResponseEntity<>(userDetailsDto, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailsDto> getUserById(@PathVariable long userId) {

        UserDetailsDto userDetailsDto = userService.getUserById(userId);

        return new ResponseEntity<>(userDetailsDto, OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDetailsDto>> getAllUsers() {

        List<UserDetailsDto> usersList = userService.getAllUsers();

        return new ResponseEntity<>(usersList, OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDetailsDto> updateUser(@PathVariable long userId,
                                                     @Valid @RequestBody UpdateUserDto updateUserDto) {

        UserDetailsDto userDetailsDto = userService.updateUser(userId, updateUserDto);

        return new ResponseEntity<>(userDetailsDto, OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable long userId) {

        userService.deleteUser(userId);

        return new ResponseEntity(OK);
    }
}
