package com.example.demo.todoRepository;

import com.example.demo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByUserName(String userName);

    Boolean existsByUserName(String userName);

    UserEntity findByUserNameAndPassword(String userName, String password);


}
