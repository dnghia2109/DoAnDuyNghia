package com.example.newsportal.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "list_blog_liked")
public class ListBlogLiked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


}
