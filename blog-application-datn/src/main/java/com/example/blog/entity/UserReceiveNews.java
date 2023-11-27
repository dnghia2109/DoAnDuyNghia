package com.example.blog.entity;

import com.example.blog.constant.EReceiveNewsState;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/*
* @author: Lai Duy Nghia
* @since: 24/10/2023 16:31
* @description: Entity này chứa thông tin cá email đăng ký nhận email thông báo về bài vết mới
* @update:
*
* */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_subcribe")
public class UserReceiveNews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EReceiveNewsState status;

    @Column(name = "subcribedAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime subcribedAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        status = EReceiveNewsState.ACTIVE;
        subcribedAt = LocalDateTime.now();
        updatedAt = subcribedAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}