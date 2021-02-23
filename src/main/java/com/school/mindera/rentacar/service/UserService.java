package com.school.mindera.rentacar.service;

import com.school.mindera.rentacar.persistence.entity.UserEntity;
import com.school.mindera.rentacar.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public UserEntity getUserById(long id){
        return userRepository.findById(id).orElse(null);
    }

    public List<UserEntity> getAllUsers(){
        return (List<UserEntity>) userRepository.findAll();
    }

    public UserEntity updateUserById(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public UserEntity deleteUser(long id){

        userRepository.deleteById(id);

        return userRepository.findById(id).orElse(null);
    }
}
