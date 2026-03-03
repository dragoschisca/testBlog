package com.example.demo.web.Dto.Comment;

import jakarta.validation.constraints.NotBlank;

public record CommentEntityDto (

     Long id,

     @NotBlank String content,

     @NotBlank String createdTime,

     @NotBlank Long userId,

     @NotBlank Long postId

//    public CommentEntityDto(Long id, String content, String createdTime, Long userId, Long postId) {
//        this.id = id;
//        this.content = content;
//        this.createdTime = createdTime;
//        this.userId = userId;
//        this.postId = postId;
//    }
){}
