package com.example.blog.mapper;

import com.example.blog.dto.BlogDto;
import com.example.blog.dto.CommentDto;
import com.example.blog.dto.TagDto;
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
        blogDto.setNote(blog.getNote());
        blogDto.setSourceURL(blog.getSourceURL());
        blogDto.setCreateType(blog.getCreateType().ordinal());
        blogDto.setViews(blog.getViews());
        blogDto.setCreatedAt(blog.getCreatedAt());
        blogDto.setPublishedAt(blog.getPublishedAt());
        blogDto.setUpdatedAt(blog.getUpdatedAt());
        blogDto.setStatus(blog.getStatus());
        blogDto.setApprovalStatus(String.valueOf(blog.getApprovalStatus()));
//        blogDto.setAuthor(new UserDto(blog.getUser()));
        blogDto.setAuthor(blog.getUser().getName());
        blogDto.setCategory(blog.getCategory());
        blogDto.setTags(blog.getTags().stream().map(TagDto::new).collect(Collectors.toList()));
        blogDto.setComments(blog.getComments().stream().map(CommentDto::new).collect(Collectors.toList()));
        return blogDto;
    }
}
