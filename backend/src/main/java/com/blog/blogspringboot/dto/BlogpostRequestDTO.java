package com.blog.blogspringboot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogpostRequestDTO {
    private String title;
    private String content;
}