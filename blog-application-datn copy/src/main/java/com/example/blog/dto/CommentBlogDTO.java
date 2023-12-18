package com.example.blog.dto;

import com.example.blog.entity.Blog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentBlogDTO {
    private Integer id;
    private String title;
    private String slug;

    public CommentBlogDTO(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.slug = blog.getSlug();
    }
}
