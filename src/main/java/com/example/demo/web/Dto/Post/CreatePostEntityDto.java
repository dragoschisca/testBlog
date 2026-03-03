package com.example.demo.Web.Dto.Post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePostEntityDto(
        @NotBlank @Size(max = 100) String title,
        @NotBlank @Size(max = 300) String content,
        @NotBlank Long userId
) {}