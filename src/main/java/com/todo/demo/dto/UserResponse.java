package com.todo.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    private boolean success;
    private String token;
    private String message;

    public static UserResponse success(String token, String message) {
        return new UserResponse(true, token, message);
    }

    public static UserResponse failure(String message) {
        return new UserResponse(false, null, message);
    }
}
