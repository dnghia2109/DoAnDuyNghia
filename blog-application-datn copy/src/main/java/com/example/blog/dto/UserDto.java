package com.example.blog.dto;

import com.example.blog.entity.User;
import lombok.*;
import com.example.blog.entity.Role;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private Integer id;
    private String name;
    private String email;
    private List<Role> roles;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }
}
