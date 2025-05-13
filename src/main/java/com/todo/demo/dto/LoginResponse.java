package com.todo.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
    private boolean success;
    private String token;
    private String message;

    public static LoginResponse success(String token, String message) {
        return new LoginResponse(true, token, message);
    }

    public static LoginResponse failure(String message) {
        return new LoginResponse(false, null, message);
    }
}
