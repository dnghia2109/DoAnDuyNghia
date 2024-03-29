package com.example.blog.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserFeedbackRequest {
    private String username;
    private String email;
    private String content;
    private String contentReply;
}
