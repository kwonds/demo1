package com.todo.demo.controller;

import com.todo.demo.dto.*;
import com.todo.demo.model.Todo;
import com.todo.demo.service.TodoService;
import com.todo.demo.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final JwtUtil jwtUtil;

    @PostMapping
    @Tag(name = "06.할일 등록")
    @Operation(description = "Authorize 토큰값 저장 필요")
    @ApiResponse(
            responseCode = "201",
            description = "할일 등록 성공",
            content = @Content(schema = @Schema(implementation = TodoResponse.class))
    )
    public ResponseEntity<TodoResponse> createTodo(
            @Parameter(hidden = true) @RequestHeader("Authorization") String authHeader,
            @RequestBody @Valid TodoRequest request) {
        String userId = jwtUtil.extractUserId(authHeader);
        todoService.createTodo(userId, request);
        return ResponseEntity.ok(TodoResponse.success("할일이 등록되었습니다."));
    }

    @GetMapping
    @Tag(name = "07.할일 전체 조회")
    @Operation(description = "Authorize 토큰값 저장 필요")
    @ApiResponse(
            responseCode = "200",
            description = "할일 전체 조회 성공",
            content = @Content(schema = @Schema(implementation = TodoListResponse.class))
    )
    public ResponseEntity<TodoListResponse> getTodos(
            @Parameter(hidden = true) @RequestHeader("Authorization") String authHeader) {
        String userId = jwtUtil.extractUserId(authHeader);
        List<Todo> todos = todoService.getAllTodos(userId);
        return ResponseEntity.ok(TodoListResponse.success(todos));
    }

    @GetMapping("/{id}")
    @Tag(name = "08.할일 조회")
    @Operation(description = "Authorize 토큰값 저장 필요")
    @ApiResponse(
            responseCode = "200",
            description = "할일 조회 성공",
            content = @Content(schema = @Schema(implementation = TodoInfoResponse.class))
    )
    public ResponseEntity<TodoInfoResponse> getTodoById(
            @Parameter(hidden = true) @RequestHeader("Authorization") String authHeader,
            @Parameter(name = "id", description = "할일 ID", example = "6") @PathVariable Long id) {
        String userId = jwtUtil.extractUserId(authHeader);
        Todo todo = todoService.getTodoById(userId, id);
        return ResponseEntity.ok(TodoInfoResponse.success(null, todo));
    }

    @PutMapping("/{id}")
    @Tag(name = "09.할일 수정")
    @Operation(description = "Authorize 토큰값 저장 필요")
    @ApiResponse(
            responseCode = "200",
            description = "할일 수정 성공",
            content = @Content(schema = @Schema(implementation = TodoResponse.class))
    )
    // eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZXIyIiwiaWF0IjoxNzQ3MTc1ODc5LCJleHAiOjE3NDcxNzk0Nzl9.kptXuVw9wCvO-wupJfzGOh74y-Zz1FYnA4N43AU7aQw
    public ResponseEntity<TodoResponse> updateTodo(
            @Parameter(hidden = true) @RequestHeader("Authorization") String authHeader,
            @Parameter(name = "id", description = "할일 ID", example = "6") @PathVariable Long id,
            @RequestBody @Valid TodoInfoRequest request) {
        String userId = jwtUtil.extractUserId(authHeader);
        todoService.updateTodo(userId, id, request);
        return ResponseEntity.ok(TodoResponse.success("할일이 수정 되었습니다."));
    }

    @DeleteMapping("/{id}")
    @Tag(name = "10.할일 삭제")
    @Operation(description = "Authorize 토큰값 저장 필요")
    @ApiResponse(
            responseCode = "200",
            description = "할일 삭제 성공",
            content = @Content(schema = @Schema(implementation = TodoResponse.class))
    )
    public ResponseEntity<TodoResponse> deleteTodo(
            @Parameter(hidden = true) @RequestHeader("Authorization") String authHeader,
            @Parameter(name = "id", description = "할일 ID", example = "6") @PathVariable Long id) {
        String userId = jwtUtil.extractUserId(authHeader);
        todoService.deleteTodo(userId, id);
        return ResponseEntity.ok(TodoResponse.success("할일이 삭제되었습니다."));
    }

    @GetMapping("/search")
    @Tag(name = "11.할일 검색")
    @Operation(description = "Authorize 토큰값 저장 필요")
    @ApiResponse(
            responseCode = "200",
            description = "할일 검색 성공",
            content = @Content(schema = @Schema(implementation = TodoListResponse.class))
    )
    public ResponseEntity<TodoListResponse> searchTodos(
            @Parameter(hidden = true) @RequestHeader("Authorization") String authHeader,
            @ModelAttribute TodoSearchRequest searchRequest) {
        String userId = jwtUtil.extractUserId(authHeader);
        List<Todo> todos = todoService.searchTodos(userId, searchRequest);
            return ResponseEntity.ok(TodoListResponse.success(todos));
    }

}