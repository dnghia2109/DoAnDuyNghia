package com.example.blog.mapper;

import com.example.blog.constant.EApprovalStatus;
import com.example.blog.dto.BlogDto;
import com.example.blog.dto.CategoryDto;
import com.example.blog.entity.Category;

import java.util.stream.Collectors;

public class CategoryMapper {
    public static CategoryDto getCategoryWithListBlogs(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setStatus(category.getStatus());
        categoryDto.setBlogs(category.getBlogs().stream()
                .filter(blog -> blog.getStatus() && blog.getApprovalStatus() == EApprovalStatus.APPROVE)
                .map(BlogDto::new).collect(Collectors.toList()));
        return categoryDto;
    }
}
