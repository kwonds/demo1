package com.todo.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoInfoRequest {

    @Schema(description = "제목", example = "제목5")
    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    @Schema(description = "할일", example = "할일5")
    @NotBlank(message = "할일을 입력해주세요.")
    private String description;

    @Schema(description = "만료일", example = "2025-05-15 12:00:00")
    @NotBlank(message = "만료일자를 입력해주세요.")
    private String dueDate;

    @Schema(description = "완료 상태", example = "true")
    private boolean completed;
}
