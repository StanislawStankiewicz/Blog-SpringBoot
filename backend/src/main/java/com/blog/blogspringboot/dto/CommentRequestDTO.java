package com.blog.blogspringboot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {
    private int blogpostId;
    private String content;
}
