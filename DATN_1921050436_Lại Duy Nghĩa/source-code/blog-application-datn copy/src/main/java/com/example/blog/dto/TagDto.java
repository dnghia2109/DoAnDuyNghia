package com.example.blog.dto;

import com.example.blog.entity.Category;
import com.example.blog.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {
    private Integer id;
    private String name;
    private Boolean status;
    private List<BlogDto> blogs;

    public TagDto(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
        this.status = tag.getStatus();
    }
}
