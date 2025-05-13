package com.todo.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
//@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoResponse {

    private boolean success;
    private String message;
    private UserInfo data;

    public static UserInfoResponse success(String message, UserInfo data) {
        return UserInfoResponse.builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static UserInfoResponse failure(String message) {
        return UserInfoResponse.builder()
                .success(false)
                .message(message)
                .data(null)
                .build();
    }
}