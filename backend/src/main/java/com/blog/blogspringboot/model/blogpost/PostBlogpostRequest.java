package com.blog.blogspringboot.model.blogpost;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostBlogpostRequest {
    private String title;
    private String content;
}