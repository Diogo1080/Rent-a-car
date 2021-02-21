package com.school.mindera.rentacar.service;

import com.school.mindera.rentacar.persistence.entity.UserEntity;
import com.school.mindera.rentacar.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(UserEntity userEntity) {

        UserEntity createdUser = userRepository.save(userEntity);

        return createdUser;
    }
}
