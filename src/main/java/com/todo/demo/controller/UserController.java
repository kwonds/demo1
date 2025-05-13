package com.todo.demo.controller;

import com.todo.demo.dto.*;
import com.todo.demo.model.User;
import com.todo.demo.service.UserService;
import com.todo.demo.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    @Tag(name = "01.회원가입")
    @ApiResponse(
            responseCode = "201",
            description = "회원가입 성공",
            content = @Content(schema = @Schema(implementation = UserResponse.class))
    )
    public ResponseEntity<UserResponse> signup(@RequestBody @Valid SignupRequest request) {
        userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponse.success(null, "회원가입이 완료되었습니다."));
    }

    @PostMapping("/login")
    @Tag(name = "02.로그인")
    @Operation(description = "로그인 후 swagger 오른쪽 상단에 Authorize 버튼 누르고 토큰값 저장 해야 나머지 테스트 가능")
    @ApiResponse(
            responseCode = "200",
            description = "로그인 성공",
            content = @Content(schema = @Schema(implementation = UserResponse.class))
    )
    public ResponseEntity<UserResponse> login(@RequestBody @Valid LoginRequest request) {
        User user = userService.isValidUser(request);
        String token = jwtUtil.generateToken(user.getUserId());
        return ResponseEntity.ok(UserResponse.success(token, "로그인 되었습니다."));
    }

    @GetMapping("/me")
    @Tag(name = "03.내정보 조회")
    @Operation(description = "Authorize 토큰값 저장 필요")
    @ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            content = @Content(schema = @Schema(implementation = UserInfoResponse.class))
    )
    public ResponseEntity<UserInfoResponse> getMyInfo(
            @Parameter(hidden = true) @RequestHeader("Authorization") String authHeader) {
        String userId = jwtUtil.extractUserId(authHeader);
        User user = userService.findUser(userId);
        UserInfo userInfo = UserInfo.of(user);
        return ResponseEntity.ok(UserInfoResponse.success(null, userInfo));
    }

    @PutMapping("/me")
    @Tag(name = "04.내정보 수정")
    @Operation(description = "Authorize 토큰값 저장 필요")
    @ApiResponse(
            responseCode = "200",
            description = "수정 성공",
            content = @Content(schema = @Schema(implementation = UserResponse.class))
    )
    public ResponseEntity<UserResponse> updateMyInfo(
            @Parameter(hidden = true) @RequestHeader("Authorization") String authHeader,
            @RequestBody @Valid UserUpdateRequest request) {
        String userId = jwtUtil.extractUserId(authHeader);
        userService.updateUser(userId, request);
        return ResponseEntity.ok(UserResponse.success(null, "사용자 정보가 수정되었습니다."));
    }

    @DeleteMapping("/me")
    @Tag(name = "05.계정 삭제")
    @Operation(description = "Authorize 토큰값 저장 필요")
    @ApiResponse(
            responseCode = "200",
            description = "삭제 성공",
            content = @Content(schema = @Schema(implementation = UserResponse.class))
    )
    public ResponseEntity<UserResponse> deleteMyInfo(
            @Parameter(hidden = true) @RequestHeader("Authorization") String authHeader) {
        String userId = jwtUtil.extractUserId(authHeader);
        userService.deactivateUser(userId);
        return ResponseEntity.ok(UserResponse.success(null, "회원 탈퇴가 완료되었습니다."));
    }

}
