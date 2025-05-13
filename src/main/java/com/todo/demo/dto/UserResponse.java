package com.todo.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private boolean success;
    private String message;
    private UserInfo data;

    public static UserResponse success(String message, UserInfo data) {
        return UserResponse.builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static UserResponse failure(String message) {
        return UserResponse.builder()
                .success(false)
                .message(message)
                .data(null)
                .build();
    }
}