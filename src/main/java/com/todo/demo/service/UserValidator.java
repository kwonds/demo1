package com.todo.demo.service;

import com.todo.demo.model.User;
import com.todo.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void duplicateUserId(String userId) {
        if (userRepository.findByUserId(userId).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다.");
        }
    }

    public void validatePassword(String rawPassword, String hashedPassword) {
        if (!BCrypt.checkpw(rawPassword, hashedPassword)) {
            throw new IllegalArgumentException("비밀번호가 다릅니다.");
        }
    }

    public User activeUserByUserId(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디 입니다."));

        if ("N".equals(user.getUseYn())) {
            throw new IllegalArgumentException("탈퇴된 아이디입니다.");
        }
        return user;
    }

    public String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
