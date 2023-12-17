package com.example.blog.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateUserRequest {
    private String name;
    private List<Integer> roleIds;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    public UpdateUserRequest(String name) {
        this.name = name;
    }
}
