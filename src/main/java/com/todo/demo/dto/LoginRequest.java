package com.todo.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @Schema(description = "사용자 ID", example = "tester5")
    @NotBlank(message = "아이디는 필수입니다.")
    private String userId;

    @Schema(description = "비밀번호", example = "1234")
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}
