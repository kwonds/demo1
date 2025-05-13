package com.todo.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoSearchRequest {
    private String title;
    private String description;
    private boolean completed;
    public String dueStartDate;
    public String dueEndDate;
}
