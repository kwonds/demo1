//package com.todo.demo.exception;
//
//import com.todo.demo.dto.TodoResponse;
//import io.jsonwebtoken.JwtException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    // 인증 실패 (401)
//    @ExceptionHandler({JwtException.class, IllegalArgumentException.class})
//    public ResponseEntity<TodoResponse> handleJwtExceptions(Exception e) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                .body(TodoResponse.failure(e.getMessage()));
//    }
//
//    // 기타 서버 오류 (500)
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<TodoResponse> handleAll(Exception e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(TodoResponse.failure("서버 내부 오류가 발생했습니다."));
//    }
//}