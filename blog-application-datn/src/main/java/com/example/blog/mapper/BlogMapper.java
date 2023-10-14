package com.example.blog.mapper;

import com.example.blog.dto.BlogDto;
import com.example.blog.dto.CommentDto;
import com.example.blog.dto.UserDto;
import com.example.blog.entity.Blog;

import java.util.stream.Collectors;

public class BlogMapper {
    public static BlogDto toDto(Blog blog) {
        BlogDto blogDto = new BlogDto();
        blogDto.setId(blog.getId());
        blogDto.setTitle(blog.getTitle());
        blogDto.setSlug(blog.getSlug());
        blogDto.setDescription(blog.getDescription());
        blogDto.setContent(blog.getContent());
        blogDto.setThumbnail(blog.getThumbnail());
        blogDto.setCreatedAt(blog.getCreatedAt());
        blogDto.setPublishedAt(blog.getPublishedAt());
        blogDto.setUpdatedAt(blog.getUpdatedAt());
        blogDto.setStatus(blog.getStatus());
        blogDto.setAuthor(new UserDto(blog.getUser()));
        blogDto.setCategory(blog.getCategory());
        blogDto.setComments(blog.getComments().stream().map(CommentDto::new).collect(Collectors.toList()));
        return blogDto;
    }
}
