package com.todo.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.todo.demo.model.Todo;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoListResponse {

    private boolean success;
    private List<Todo> todos;

    public static TodoListResponse success(List<Todo> todos) {
        return TodoListResponse.builder()
                .success(true)
                .todos(todos)
                .build();
    }

    public static TodoListResponse failure(String message) {
        return TodoListResponse.builder()
                .success(false)
                .build();
    }
}