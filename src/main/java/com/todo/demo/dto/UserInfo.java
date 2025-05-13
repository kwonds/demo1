package com.todo.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo {
    private String userId;
    private String userName;
    private String email;
    private String registDate;
    private String updateDate;
    private String password;
}