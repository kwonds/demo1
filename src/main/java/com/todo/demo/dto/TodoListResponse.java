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
public class TodoListResponse {
    private boolean success;
    private String message;
    private List<Todo> todos;

    public static TodoListResponse success(String message, List<Todo> todos) {
        return TodoListResponse.builder()
                .success(true)
                .message(message)
                .todos(todos)
                .build();
    }

    public static TodoListResponse failure(String message) {
        return TodoListResponse.builder()
                .success(false)
                .message(message)
                .build();
    }
}