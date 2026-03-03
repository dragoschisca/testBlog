package com.example.demo.Web.Dto.Comment;

import com.example.demo.Domain.PostEntity;
import com.example.demo.Domain.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class CommentEntityDto {

    private Long id;

    private String content;

    private String createdTime;

    private Long userId;

    private Long postId;

    public CommentEntityDto(Long id, String content, String createdTime, Long userId, Long postId) {
        this.id = id;
        this.content = content;
        this.createdTime = createdTime;
        this.userId = userId;
        this.postId = postId;
    }
}
