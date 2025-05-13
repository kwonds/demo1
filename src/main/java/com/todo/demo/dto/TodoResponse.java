package com.todo.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.todo.demo.model.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoResponse {
    private boolean success;
    private String message;
    private Todo todo;

    public static TodoResponse success(String message, Todo todo) {
        return TodoResponse.builder()
                .success(true)
                .message(message)
                .todo(todo)
                .build();
    }

    public static TodoResponse failure(String message) {
        return TodoResponse.builder()
                .success(false)
                .message(message)
                .build();
    }
}