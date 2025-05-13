package com.todo.demo.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    private String userId;
    private String userName;
    private String password;
    private String email;
}