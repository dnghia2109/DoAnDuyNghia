package com.example.blog.dto;

import com.example.blog.constant.EApprovalStatus;
import com.example.blog.entity.Category;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDto {
    private Integer id;
    private String name;
    private Boolean status;
    private List<BlogDto> blogs;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.status = category.getStatus();
    }
}
