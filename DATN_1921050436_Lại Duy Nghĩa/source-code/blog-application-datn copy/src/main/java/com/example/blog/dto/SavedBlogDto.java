package com.example.blog.dto;

import com.example.blog.entity.SavedBlog;
import javax.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SavedBlogDto {
    private Integer id;
    private BlogDto blog;
    private UserDto user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SavedBlogDto(SavedBlog savedBlog) {
        this.id = savedBlog.getId();
        this.blog = new BlogDto(savedBlog.getBlog());
        this.user = new UserDto(savedBlog.getUser());
        this.createdAt = savedBlog.getCreatedAt();
        this.updatedAt = savedBlog.getUpdatedAt();
    }
}
