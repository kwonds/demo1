package com.todo.demo.controller;

import com.todo.demo.dto.LoginRequest;
import com.todo.demo.dto.SignupRequest;
import com.todo.demo.dto.UserInfo;
import com.todo.demo.model.User;
import com.todo.demo.service.UserService;
import com.todo.demo.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void signup() {
        SignupRequest signupRequest = SignupRequest.builder()
                .userId("tester7")
                .userName("테스터7")
                .password("1234")
                .email("tester7@gmail.com")
                .build();
        User user = userService.registerUser(signupRequest);
        System.out.println(user.toString());
    }

    @Test
    void login() {
        LoginRequest loginRequest = LoginRequest.builder()
                .userId("tester7")
                .password("1234")
                .build();
        User user = userService.isValidUser(loginRequest);
        System.out.println(user.toString());
    }

    @Test
    void findUser() {
        User user = userService.findUser("tester7");
        UserInfo userInfo = UserInfo.of(user);
        System.out.println(userInfo.toString());
    }
}