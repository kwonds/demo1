package com.todo.demo.service;

import com.todo.demo.dto.LoginRequest;
import com.todo.demo.dto.SignupRequest;
import com.todo.demo.dto.UserInfo;
import com.todo.demo.model.User;
import com.todo.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User registerUser(SignupRequest request) {
        if (userRepository.findByUserId(request.getUserId()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다.");
        }

        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());

        User user = User.builder()
                .userId(request.getUserId())
                .userName(request.getUserName())
                .password(hashedPassword)
                .email(request.getEmail())
                .build();

        return userRepository.save(user);
    }

    public User findUser(LoginRequest request) {
        Optional<User> optionalUser = userRepository.findByUserId(request.getUserId());

        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 아이디 입니다.");
        }

        User user = optionalUser.get();

        if ("N".equals(user.getUseYn())) {
            throw new IllegalArgumentException("탈퇴된 아이디입니다.");
        }

        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 다릅니다.");
        }

        return user;
    }

    public User findUser(String userId) {
        return userRepository.findByUserIdAndUseYn(userId, "Y")
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디 입니다."));
    }

    public void updateUser(String userId, UserInfo request) {
        User user = userRepository.findByUserIdAndUseYn(userId, "Y")
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        user.setUserName(request.getUserName());
        user.setPassword(hashedPassword);
        user.setEmail(request.getEmail());

        userRepository.save(user);
    }

    public void deactivateUser(String userId) {
        User user = userRepository.findByUserIdAndUseYn(userId, "Y")
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        user.setUseYn("N");
        userRepository.save(user);
    }

}
