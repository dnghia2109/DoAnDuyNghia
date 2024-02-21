package com.example.blog.dto;

import com.example.blog.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CommentDto {
    private int id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDto author;
    private CommentBlogDTO blog;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.author = new UserDto(comment.getUser());
        this.blog = new CommentBlogDTO(comment.getBlog());
    }
}
