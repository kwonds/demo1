package com.todo.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoInfo {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private String dueDate;
    private String registDate;
    private String updateDate;
}