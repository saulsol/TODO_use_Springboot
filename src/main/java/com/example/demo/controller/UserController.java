package com.example.demo.controller;


import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.UserEntity;
import com.example.demo.security.TokenProvider;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private UserService userService;

    private TokenProvider tokenProvider;

    @PostMapping("/signup") // 회원가입
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){

        try{
            if(userDTO == null || userDTO.getPassword() == null) {
                throw new RuntimeException("Invalid Password value.");
            }

            // 요청을 이용해 저장할 유저 만들기

            UserEntity user = UserEntity.builder()
                    .userName(userDTO.getUserName())
                    .password(userDTO.getPassword())
                    .build();

            //서비스를 이용해 레포에 유저 저장 
            UserEntity registeredUser = userService.create(user);

            //UserResponseDTO 생성
            UserDTO responseUserDTO = UserDTO.builder()
                    .id(registeredUser.getId())
                    .userName(registeredUser.getUserName())
                    .build();



            return ResponseEntity.ok().body(responseUserDTO);

        } catch (Exception e) {

            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDTO);

        }


    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO){

        UserEntity user = userService.getByCredentials(
                userDTO.getUserName(),
                userDTO.getPassword()
        );

        if(user != null ){

            // 토큰 생성
            final String token = tokenProvider.create(user);


            final UserDTO responseUserDTO = UserDTO.builder()
                    .userName(user.getUserName())
                    .id(user.getId())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        }else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login failed")
                    .build();

            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }


    }







}
