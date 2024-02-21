package com.example.blog.dto;

import com.example.blog.entity.Blog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BlogSendMailDto {
    private Integer id;
    private String title;
    private String slug;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime publishedAt;
    private LocalDateTime updatedAt;
    private String blogUrl;

    public BlogSendMailDto (Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.slug = blog.getSlug();
        this.description = blog.getDescription();
        this.createdAt = blog.getCreatedAt();
        this.publishedAt = blog.getPublishedAt();
        this.updatedAt = blog.getUpdatedAt();
    }
}
