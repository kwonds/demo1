package com.todo.demo.service;

import com.todo.demo.dto.LoginRequest;
import com.todo.demo.dto.SignupRequest;
import com.todo.demo.dto.UserInfo;
import com.todo.demo.dto.UserUpdateRequest;
import com.todo.demo.model.User;
import com.todo.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserValidator userValidator;
    private final UserRepository userRepository;

    public User registerUser(SignupRequest request) {
        userValidator.duplicateUserId(request.getUserId());
        String encodedPassword = userValidator.encryptPassword(request.getPassword());

        User user = User.of(request, encodedPassword);
        return userRepository.save(user);
    }

    public User isValidUser(LoginRequest request) {
        User user = userValidator.activeUserByUserId(request.getUserId());
        userValidator.validatePassword(request.getPassword(), user.getPassword());
        return user;
    }

    public User findUser(String userId) {
        return userValidator.activeUserByUserId(userId);
    }

    public void updateUser(String userId, UserUpdateRequest request) {
        User user = userValidator.activeUserByUserId(userId);
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());

        user.setUserName(request.getUserName());
        user.setPassword(hashedPassword);
        user.setEmail(request.getEmail());

        userRepository.save(user);
    }

    public void deactivateUser(String userId) {
        User user = userValidator.activeUserByUserId(userId);
        user.setUseYn("N");
        userRepository.save(user);
    }

}
