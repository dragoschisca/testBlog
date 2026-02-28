package com.example.demo.Web.Controllers;

import com.example.demo.Domain.CommentEntity;
import com.example.demo.Service.CommentService;
import com.example.demo.Web.Dto.Comment.CommentEntityDto;
import com.example.demo.Web.Dto.Comment.CreateCommentEntityDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @GetMapping
    public Page<CommentEntity> findAll(Pageable pageable) {
        return commentService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentEntity> findById(@PathVariable Long id) {
        return commentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<CommentEntityDto> create(@RequestBody CreateCommentEntityDto dto) {
        return ResponseEntity.ok(commentService.create(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<CommentEntityDto> update(@PathVariable Long id,  @RequestBody CreateCommentEntityDto dto) {

        return commentService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = commentService.delete(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }
}