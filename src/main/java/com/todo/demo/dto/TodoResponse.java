package com.todo.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.todo.demo.model.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoResponse {

    private boolean success;
    private String message;

    public static TodoResponse success(String message) {
        return TodoResponse.builder()
                .success(true)
                .message(message)
                .build();
    }

    public static TodoResponse failure(String message) {
        return TodoResponse.builder()
                .success(false)
                .message(message)
                .build();
    }
}