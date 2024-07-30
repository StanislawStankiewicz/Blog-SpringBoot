package com.blog.blogspringboot.model.blogpost;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HeartBlogpostResponse {

    private String message;

    private boolean hearted;

}
