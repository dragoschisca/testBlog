package com.example.demo.Web.Dto.Post;

import java.time.LocalDateTime;

public class PostEntityDto{
        private Long id;
        private String title;
        private String content;
        private String createdTime;
        private Long userId;

    public PostEntityDto(Long id, String title, String content, String createdTime, Long userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdTime = createdTime;
        this.userId = userId;
    }

}