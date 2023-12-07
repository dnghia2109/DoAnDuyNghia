package com.example.blog.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFeedbackRequest {
    private String email;
    private String content;
    private String contentReply;
}
