package com.todo.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoSearchRequest {

    @Schema(description = "제목", example = "할일")
    private String title;

    @Schema(description = "할일",  example = "")
    private String description;

    @Schema(description = "완료 상태", example = "false")
    private boolean completed;

    @Schema(description = "시작 만료일", example = "2025-05-10 12:00:00")
    public String dueStartDate;

    @Schema(description = "종료 만료일", example = "2025-05-15 12:00:00")
    public String dueEndDate;
}
