package com.todo.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.todo.demo.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo {

    private String userId;
    private String userName;
    private String email;
    private String registDate;
    private String updateDate;
    private String password;

    public static UserInfo of(User user) {
        return UserInfo.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .registDate(user.getRegistDate())
                .updateDate(user.getUpdateDate())
                .build();
    }
}