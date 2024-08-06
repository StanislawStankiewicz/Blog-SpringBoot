package com.blog.blogspringboot.model.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCommentRequest {
    private int blogpostId;
    private String content;
}
