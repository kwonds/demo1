package com.todo.demo.controller;

import com.todo.demo.dto.*;
import com.todo.demo.model.User;
import com.todo.demo.service.UserService;
import com.todo.demo.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> signup(@RequestBody SignupRequest request) {
        try {
            User user = userService.registerUser(request);
            String token = jwtUtil.generateToken(user.getUserId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(LoginResponse.success(token, "회원가입이 완료되었습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(LoginResponse.failure(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            User user = userService.findUser(request);
            String token = jwtUtil.generateToken(user.getUserId());
            return ResponseEntity.ok(LoginResponse.success(token, ""));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(LoginResponse.failure(e.getMessage()));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> myInfo(@RequestHeader("Authorization") String authHeader) {
        try {
            String userId = extractUserIdFromToken(authHeader);
            User user = userService.findUser(userId);
            UserInfo userInfo = buildUserInfo(user);

            return ResponseEntity.ok(UserResponse.success(null, userInfo));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(UserResponse.failure("토큰이 만료되었습니다. 다시 로그인하세요."));
        } catch (JwtException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(UserResponse.failure(e.getMessage()));
        }
    }

    @PutMapping("/me")
    public ResponseEntity<LoginResponse> updateMyInfo(@RequestHeader("Authorization") String authHeader,
                                                      @RequestBody UserInfo request) {
        try {
            String userId = extractUserIdFromToken(authHeader);
            userService.updateUser(userId, request);
            return ResponseEntity.ok(LoginResponse.success(null, "사용자 정보가 수정되었습니다."));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(LoginResponse.failure("토큰이 만료되었습니다."));
        } catch (JwtException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(LoginResponse.failure(e.getMessage()));
        }
    }

    @DeleteMapping("/me")
    public ResponseEntity<LoginResponse> deleteMyInfo(@RequestHeader("Authorization") String authHeader) {
        try {
            String userId = extractUserIdFromToken(authHeader);
            userService.deactivateUser(userId); // 실제 삭제 대신 상태만 변경
            return ResponseEntity.ok(LoginResponse.success(null, "회원 탈퇴가 완료되었습니다."));
        } catch (JwtException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(LoginResponse.failure(e.getMessage()));
        }
    }

    // 유틸 메서드
    private String extractUserIdFromToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return jwtUtil.extractUserId(token);
    }

    private UserInfo buildUserInfo(User user) {
        return UserInfo.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .registDate(user.getRegistDate())
                .updateDate(user.getUpdateDate())
                .build();
    }

}
