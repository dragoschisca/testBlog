package com.example.demo.web.Dto.Comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCommentEntityDto (

    @NotBlank @Size(max = 100) String content,
    @NotBlank Long userId,
    @NotBlank Long postId
){}
