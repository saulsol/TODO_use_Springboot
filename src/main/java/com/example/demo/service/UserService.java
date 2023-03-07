package com.example.demo.service;

import com.example.demo.model.UserEntity;
import com.example.demo.todoRepository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {


    private UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity){

        if(userEntity == null || userEntity.getUserName() == null){
            throw new RuntimeException("Invalid arguments");
        }

        final String username = userEntity.getUserName();

        if(userRepository.existsByUserName(username)){
            log.warn("UserName already exists {}", username);
            throw new RuntimeException("Username already exists");
        }

        return userRepository.save(userEntity);
    }


    public UserEntity getByCredentials(final String username, final String password){
        return userRepository.findByUserNameAndPassword(username, password);
    }






}
