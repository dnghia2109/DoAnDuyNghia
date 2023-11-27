package com.example.blog.dto;

import com.example.blog.constant.EApprovalStatus;
import com.example.blog.entity.Blog;
import com.example.blog.entity.Category;
import com.example.blog.entity.Comment;
import com.example.blog.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BlogDto {
    private Integer id;
    private String title;
    private String slug;
    private String description;
    private String content;
    private String thumbnail;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime publishedAt;
    private LocalDateTime updatedAt;
    private Boolean status;
    private String approvalStatus;
    private String author;
    private Category category;
    private List<TagDto> tags;
    private List<CommentDto> comments;

    public BlogDto(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.slug = blog.getSlug();
        this.description = blog.getDescription();
        this.content = blog.getContent();
        this.thumbnail = blog.getThumbnail();
        this.note = blog.getNote();
        this.status = blog.getStatus();
        this.approvalStatus = blog.getApprovalStatus().toString();
        this.publishedAt = blog.getPublishedAt();
        this.createdAt = blog.getCreatedAt();
        this.updatedAt = blog.getUpdatedAt();
//        this.author = new UserDto(blog.getUser());
        this.author = blog.getUser().getName();
        this.category = blog.getCategory();
        this.tags = blog.getTags().stream().map(TagDto::new).collect(Collectors.toList());
        this.comments = blog.getComments().stream().map(CommentDto::new).collect(Collectors.toList());
    }
}
