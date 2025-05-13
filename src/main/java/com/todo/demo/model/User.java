package com.todo.demo.model;

import com.todo.demo.dto.SignupRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //
    @Column(nullable = false) // unique = true 제거, SQLite는 ALTER TABLE로 UNIQUE 제약 추가 안 됨
    private String userId; // 로그인용 ID

    private String userName; // 사용자 이름

    @Column(nullable = false)
    private String password;

    private String email;

    @Builder.Default
    private String useYn = "Y";

    private String registDate;

    private String updateDate;

    // 날짜 포맷용
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 등록 시 자동 설정
    @PrePersist
    protected void onCreate() {
        String now = LocalDateTime.now().format(FORMATTER);
        this.registDate = now;
        this.updateDate = now;
    }

    // 수정 시 자동 설정
    @PreUpdate
    protected void onUpdate() {
        this.updateDate = LocalDateTime.now().format(FORMATTER);
    }

    public static User of(SignupRequest request, String encodedPassword) {
        return User.builder()
                .userId(request.getUserId())
                .userName(request.getUserName())
                .password(encodedPassword)
                .email(request.getEmail())
                .build();
    }

}
