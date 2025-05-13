package com.todo.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.todo.demo.model.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoInfoResponse {

    private boolean success;
    private String message;
    private Todo todo;

    public static TodoInfoResponse success(String message, Todo todo) {
        return TodoInfoResponse.builder()
                .success(true)
                .message(message)
                .todo(todo)
                .build();
    }

    public static TodoInfoResponse failure(String message) {
        return TodoInfoResponse.builder()
                .success(false)
                .message(message)
                .build();
    }
}