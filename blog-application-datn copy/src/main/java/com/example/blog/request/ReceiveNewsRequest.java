package com.example.blog.request;

import com.example.blog.constant.EReceiveNewsState;
import lombok.Data;

@Data
public class ReceiveNewsRequest {
    private String email;
    private EReceiveNewsState status;
}
