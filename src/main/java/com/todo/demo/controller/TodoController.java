package com.todo.demo.controller;

import com.todo.demo.dto.TodoInfo;
import com.todo.demo.dto.TodoListResponse;
import com.todo.demo.dto.TodoResponse;
import com.todo.demo.dto.TodoSearchRequest;
import com.todo.demo.model.Todo;
import com.todo.demo.service.TodoService;
import com.todo.demo.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(
            @RequestHeader("Authorization") String authHeader, @RequestBody Todo request) {
        try{
            String userId = extractUserIdFromToken(authHeader);
            todoService.createTodo(userId, request);
            return ResponseEntity.ok(TodoResponse.success(null, null));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(TodoResponse.failure("토큰이 만료되었습니다. 다시 로그인하세요."));
        } catch (JwtException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(TodoResponse.failure(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<TodoListResponse> getTodos(@RequestHeader("Authorization") String authHeader) {
        try {
            String userId = extractUserIdFromToken(authHeader);
            List<Todo> todos = todoService.getAllTodos(userId);
            return ResponseEntity.ok(TodoListResponse.success(null, todos));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(TodoListResponse.failure("토큰이 만료되었습니다. 다시 로그인하세요."));
        } catch (JwtException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(TodoListResponse.failure(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(
            @RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        try {
            validateAuthHeader(authHeader);
            String userId = extractUserIdFromToken(authHeader);
            Todo todo = todoService.getTodoById(userId, id);
            return ResponseEntity.ok(TodoResponse.success(null, todo));
        } catch (JwtException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(TodoResponse.failure(e.getMessage()));
        }  catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(TodoResponse.failure(e.getReason()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(TodoResponse.failure("서버 내부 오류가 발생했습니다."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long id,
            @RequestBody @Valid Todo request) {
        try {
            validateAuthHeader(authHeader);
            String userId = extractUserIdFromToken(authHeader);
            todoService.updateTodo(userId, id, request);
            return ResponseEntity.ok(TodoResponse.success(null, null));
        } catch (JwtException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(TodoResponse.failure(e.getMessage()));
        }  catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(TodoResponse.failure(e.getReason()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(TodoResponse.failure(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TodoResponse> deleteTodo(
            @RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        try {
            validateAuthHeader(authHeader);
            String userId = extractUserIdFromToken(authHeader);
            todoService.deleteTodo(userId, id);
            return ResponseEntity.ok(TodoResponse.success(null, null));
        } catch (JwtException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(TodoResponse.failure(e.getMessage()));
        }  catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(TodoResponse.failure(e.getReason()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(TodoResponse.failure(e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<TodoListResponse> searchTodos(
            @RequestHeader("Authorization") String authHeader,
            @ModelAttribute TodoSearchRequest searchRequest) {
        try {
            validateAuthHeader(authHeader);
            String userId = extractUserIdFromToken(authHeader);
            List<Todo> todos = todoService.searchTodos(userId, searchRequest);
            return ResponseEntity.ok(TodoListResponse.success(null, todos));
        } catch (JwtException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(TodoListResponse.failure(e.getMessage()));
        }  catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(TodoListResponse.failure(e.getReason()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(TodoListResponse.failure(e.getMessage()));
        }
    }

    // 유틸 메서드
    private void validateAuthHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new JwtException("인증 토큰이 없습니다.");
        }
    }

    private String extractUserIdFromToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return jwtUtil.extractUserId(token);
    }








}
