package com.todo.demo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.demo.dto.LoginRequest;
import com.todo.demo.dto.SignupRequest;
import com.todo.demo.dto.UserUpdateRequest;
import com.todo.demo.model.User;
import com.todo.demo.service.UserService;
import com.todo.demo.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@Import(UserControllerTest.MockConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }

        @Bean
        public JwtUtil jwtUtil() {
            return Mockito.mock(JwtUtil.class);
        }
    }

    @Test
    @DisplayName("회원가입 성공")
    void signupSuccess() throws Exception {
        // given
        SignupRequest request = new SignupRequest();
        request.setUserId("tester6");
        request.setUserName("테스터6");
        request.setPassword("1234");
        request.setEmail("tester6@gmail.com");

        User dummyUser = new User();
        dummyUser.setUserId("tester6");

        Mockito.when(userService.registerUser(any(SignupRequest.class))).thenReturn(dummyUser);
        Mockito.when(jwtUtil.generateToken("tester6")).thenReturn("mocked-jwt-token");

        // when & then
        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.token").value("mocked-jwt-token"))
                .andExpect(jsonPath("$.message").value("회원가입이 완료되었습니다."));
    }

    @Test
    @DisplayName("회원가입 IllegalArgumentException")
    void signupIllegalArgumentException() throws Exception {
        // given
        SignupRequest request = new SignupRequest();
        request.setUserId("tester5");
        request.setUserName("테스터5");
        request.setPassword("1234");
        request.setEmail("tester5@gmail.com");

        Mockito.when(userService.registerUser(any(SignupRequest.class)))
                .thenThrow(new IllegalArgumentException("이미 존재하는 사용자입니다."));

        // when & then
        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("이미 존재하는 사용자입니다."));
    }

    @Test
    @DisplayName("회원가입 Exception")
    void signupException() throws Exception {
        // given
        SignupRequest request = new SignupRequest();
        request.setUserId("tester6");
        request.setUserName("테스터6");
        request.setPassword("1234");
        request.setEmail("tester6@gmail.com");

        // 강제로 service 내부에서 예외 발생시킴
        Mockito.when(userService.registerUser(any()))
                .thenThrow(new RuntimeException("DB 에러"));

        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("회원가입 MethodArgumentNotValidException")
    void signupMethodArgumentNotValidException() throws Exception {
        // given
        SignupRequest request = new SignupRequest();
        request.setUserId(""); // 아이디 비움 → NotBlank 터짐
        request.setUserName("테스터6");
        request.setPassword("1234");
        request.setEmail("tester6@gmail.com");

        // when & then
        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("아이디는 필수입니다."));

//        MvcResult result = mockMvc.perform(post("/users/signup")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isBadRequest())
//                .andReturn();
//
//        String responseJson = result.getResponse().getContentAsString();
//        System.out.println("📦 raw response body: " + responseJson);
    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("tester7", "1234");
        User mockUser = new User();
        mockUser.setUserId("tester7");

        Mockito.when(userService.isValidUser(any(LoginRequest.class))).thenReturn(mockUser);
        Mockito.when(jwtUtil.generateToken("tester7")).thenReturn("mocked.jwt.token");

        // when & then
        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.token").value("mocked.jwt.token"))
                .andExpect(jsonPath("$.message").value("로그인 되었습니다."));
    }

    @Test
    @DisplayName("로그인 IllegalArgumentException")
    void loginIllegalArgumentException() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("tester77", "1234");

        Mockito.when(userService.isValidUser(any(LoginRequest.class)))
                .thenThrow(new IllegalArgumentException("존재하지 않는 아이디 입니다."));

        // when & then
        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("존재하지 않는 아이디 입니다."));
    }

    @Test
    @DisplayName("내정보 조회 성공")
    void getMyInfoSuccess() throws Exception {
        // given
        String userId = "tester7";
        String token = jwtUtil.generateToken(userId); // mocked.token으로 리턴될 것

        User mockUser = User.builder()
                .userId(userId)
                .build();

        // 🔥 핵심 추가
        Mockito.when(jwtUtil.extractUserId("Bearer " + token)).thenReturn(userId);

        Mockito.when(userService.findUser(userId)).thenReturn(mockUser);

        // when & then
        mockMvc.perform(get("/users/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.userId").value(userId));
    }

    @Test
    @DisplayName("내정보 조회 ExpiredJwtException")
    void getMyInfoExpiredJwtException() throws Exception {
        // given
        String expiredToken = "Bearer expired.jwt.token";

        Mockito.when(jwtUtil.extractUserId(expiredToken))
                .thenThrow(new ExpiredJwtException(null, null, "토큰이 만료되었습니다. 다시 로그인하세요."));

        // when & then
        mockMvc.perform(get("/users/me")
                        .header("Authorization", expiredToken))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("토큰이 만료되었습니다. 다시 로그인하세요."));
    }

    @Test
    @DisplayName("내정보 수정 성공")
    void updateMyInfoSuccess() throws Exception {
        // given
        String userId = "tester7";
        String token = "Bearer valid.token";
        String newToken = "Bearer new.token";

        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setUserName("테스터7_1");
        updateRequest.setEmail("tester7_1@email.com");
        updateRequest.setPassword("1234");

        // mock 설정
        Mockito.when(jwtUtil.extractUserId(token)).thenReturn(userId);
        Mockito.doNothing().when(userService).updateUser(userId, updateRequest);
        Mockito.when(jwtUtil.generateToken(userId)).thenReturn(newToken);

        // when & then
        mockMvc.perform(put("/users/me")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.token").value(newToken))
                .andExpect(jsonPath("$.message").value("사용자 정보가 수정되었습니다."));
    }

    @Test
    @DisplayName("내정보 수정 MissingRequestHeaderException")
    void updateMyInfoMissingRequestHeaderException() throws Exception {
        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setUserName("변경된이름");
        updateRequest.setEmail("new@email.com");

        mockMvc.perform(put("/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Authorization 헤더가 없습니다.")); // 전역 예외 메시지
    }

    @Test
    @DisplayName("계정 삭제")
    void deleteMyInfoSuccess() throws Exception {
        // given
        String userId = "tester6";
        String token = "Bearer valid.token";

        // mock 설정
        Mockito.when(jwtUtil.extractUserId(token)).thenReturn(userId);
        Mockito.doNothing().when(userService).deactivateUser(userId);

        // when & then
        mockMvc.perform(delete("/users/me")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("회원 탈퇴가 완료되었습니다."));
    }

}
