package com.todo.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private String token;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(String token, String message, T data) {
        return new ApiResponse<>(true, token, message, data);
    }

    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(false, null, message, null);
    }
}
