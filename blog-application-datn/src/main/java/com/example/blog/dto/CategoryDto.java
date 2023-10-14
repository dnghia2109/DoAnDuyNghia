package com.example.blog.dto;

import com.example.blog.entity.Category;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDto {
    private Integer id;
    private String name;
    private Boolean status;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.status = category.getStatus();
    }
}
