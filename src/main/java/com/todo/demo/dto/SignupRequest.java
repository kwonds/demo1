package com.todo.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @Schema(description = "사용자 ID", example = "tester4")
    @NotBlank(message = "아이디는 필수입니다.")
    private String userId;

    @Schema(description = "사용자 이름", example = "테스터4")
    @NotBlank(message = "이름은 필수입니다.")
    private String userName;

    @Schema(description = "비밀번호", example = "1234")
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    @Schema(description = "이메일", example = "tester4@gmail.com")
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;
}