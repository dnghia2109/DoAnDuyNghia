package com.example.blog.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class UpsertBlogRequest {
    private String title;
    private String description;
    private String content;
    private String thumbnail;
    private Boolean status;
    private String note;
    private Integer categoryId; // Danh sách id của các category áp dụng
}
