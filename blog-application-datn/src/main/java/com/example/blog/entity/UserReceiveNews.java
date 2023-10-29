package com.example.blog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/*
* @author: Lai Duy Nghia
* @since: 24/10/2023 16:31
* @description: Entity này chứa thông tin cá email đăng ký nhận email thông báo về bài vết mới
* @update:
*
* */


@Getter
@Setter
@Entity
@Table(name = "user_subcribe")
public class UserReceiveNews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;


}