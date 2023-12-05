package com.example.blog.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateUserRequest {
    private String name;
    private String password;
    private List<Integer> roleIds;
}
