package com.example.blog.request;

import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BlogCrawlDataRequest {
    List<String> urls;
}
