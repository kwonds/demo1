package com.todo.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "todos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "제목은 필수 입력값입니다.")
    @Column(nullable = false)
    private String title;

    private String description;

    private boolean completed;

    private String dueDate;

    private String registDate;

    private String updateDate;

    private Long writerId;

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

//    @ManyToOne(fetch = FetchType.LAZY) // 하나의 User가 여러 toodo를 작성할 수 있음
//    @JoinColumn(name = "writer_id", nullable = false) // FK 이름
//    private User writer; // Users의 id와 연결됨
}
