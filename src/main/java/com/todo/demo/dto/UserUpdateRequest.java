package com.todo.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.todo.demo.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserUpdateRequest {

    @Schema(description = "사용자 이름", example = "테스터0")
    @NotBlank(message = "이름은 필수입니다.")
    private String userName;

    @Schema(description = "이메일", example = "tester0@gmail.com")
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @Schema(description = "비밀번호", example = "5678")
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

}