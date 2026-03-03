package com.example.demo.web.Dto.Post;

public record PostEntityDto(
         Long id,
         String title,
         String content,
         String createdTime,
         Long userId

//    public PostEntityDto(Long id, String title, String content, String createdTime, Long userId) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//        this.createdTime = createdTime;
//        this.userId = userId;
//    }

){}