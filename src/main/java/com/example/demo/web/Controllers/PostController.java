package com.example.demo.Web.Controllers;

import com.example.demo.Domain.CommentEntity;
import com.example.demo.Domain.Enums.PostStatusEnum;
import com.example.demo.Domain.PostEntity;
import com.example.demo.Service.CommentService;
import com.example.demo.Service.PostService;
import com.example.demo.Web.Dto.Comment.CommentEntityDto;
import com.example.demo.Web.Dto.Post.CreatePostEntityDto;
import com.example.demo.Web.Dto.Post.PostEntityDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping
    public Page<PostEntity> getAll(Pageable pageable) {
        return postService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostEntity> getById(@PathVariable Long id) {
        return postService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/comments")
    public Page<CommentEntity> getCommentsForPost(
            @PathVariable Long id,
            Pageable pageable) {

        return commentService.findByPostId(id, pageable);
    }

    @PostMapping
    public ResponseEntity<PostEntityDto> create(@RequestBody CreatePostEntityDto dto) {
        return ResponseEntity.ok(postService.create(dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PostEntityDto> changeStatus(
            @PathVariable Long id,
            @RequestParam PostStatusEnum status) {

        return ResponseEntity.ok(
                postService.changeStatus(id, status)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostEntityDto> update(@PathVariable Long id, @RequestBody CreatePostEntityDto dto) {
        Optional<PostEntityDto> updated = postService.update(id, dto);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (postService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}