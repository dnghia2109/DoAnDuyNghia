package com.example.newsportal.response;

import com.techlead.javaspring.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthLoginResponse {
    private UserDto userDto;
    private String token;
    private Boolean isAuthenticated;
}
