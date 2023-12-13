package com.example.blog.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdvertisementRequest {
    private String name;
    private String linkRedirect;
    private String url;
    private Boolean status;
    private Integer position;
}
