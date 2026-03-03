package com.example.demo.Service;


import com.example.demo.Domain.CommentEntity;
import com.example.demo.Domain.PostEntity;
import com.example.demo.Domain.UserEntity;
import com.example.demo.Repository.CommentRepository;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Web.Dto.Comment.CommentEntityDto;
import com.example.demo.Web.Dto.Comment.CreateCommentEntityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public Page<CommentEntity> findAll(Pageable pageable) {
        LOGGER.info("Fetching comment page={} size={}", pageable.getPageNumber(), pageable.getPageSize());
        return commentRepository.findAll(pageable);
    }

    public Optional<CommentEntity> findById(Long id) {
        LOGGER.info("Fetching comment by id={}", id);
        return commentRepository.findById(id);
    }

    public CommentEntityDto create(CreateCommentEntityDto dto) {

        LOGGER.info("Creating comment for post={} by user={}", dto.postId(), dto.userId());

        UserEntity user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        PostEntity post = postRepository.findById(dto.postId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.content());
        entity.setCreatedTime(LocalDateTime.now());
        entity.setUser(user);
        entity.setPost(post);

        CommentEntity saved = commentRepository.save(entity);

        return mapToDto(saved);
    }

    public Optional<CommentEntityDto> update(Long id, CreateCommentEntityDto dto) {
        LOGGER.info("Updating comment for post={} by user={}", dto.postId(), dto.userId());
        return commentRepository.findById(id).map(existing -> {

            existing.setContent(dto.content());
            commentRepository.save(existing);

            return mapToDto(existing);
        });
    }

    public boolean delete(Long id) {
        LOGGER.info("Deleting comment id={}", id);
        if (!commentRepository.existsById(id)) {
            return false;
        }
        commentRepository.deleteById(id);
        return true;
    }

    private CommentEntityDto mapToDto(CommentEntity entity) {
        return new CommentEntityDto(
                entity.getId(),
                entity.getContent(),
                entity.getFormattedCreateTime(),
                entity.getUser().getId(),
                entity.getPost().getId()
        );
    }

    //FIND ALL COMMENTS BY POST ID
// FIND ALL COMMENTS BY POST ID
    public Page<CommentEntity> findByPostId(Long postId, Pageable pageable) {

        postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return commentRepository.findByPostId(postId, pageable);
    }

    //FIND ALL COMMENTS BY USER ID
// FIND ALL COMMENTS BY USER ID
    public Page<CommentEntity> findByUserId(Long userId, Pageable pageable) {

        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return commentRepository.findByUserId(userId, pageable);
    }
}