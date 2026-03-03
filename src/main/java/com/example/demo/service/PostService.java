package com.example.demo.Service;

import com.example.demo.Domain.Enums.PostStatusEnum;
import com.example.demo.Domain.PostEntity;
import com.example.demo.Domain.PostStatusEntity;
import com.example.demo.Domain.UserEntity;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.PostStatusRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Web.Dto.Post.CreatePostEntityDto;
import com.example.demo.Web.Dto.Post.PostEntityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostStatusRepository postStatusRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, PostStatusRepository postStatusRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postStatusRepository = postStatusRepository;
    }

    public Page<PostEntity> findAll(Pageable pageable) {
        LOGGER.info("Fetching users page={} size={}", pageable.getPageNumber(), pageable.getPageSize());
        return postRepository.findAll(pageable);
    }

    public Optional<PostEntity> findById(Long id) {
        LOGGER.info("Fetching user by id={}", id);
        return postRepository.findById(id);
    }

    public PostEntityDto create(CreatePostEntityDto dto) {
        LOGGER.info("Creating post title={}", dto.title());

        UserEntity user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        PostStatusEntity defaultStatus = postStatusRepository.findById(PostStatusEnum.DRAFT.name())
                .orElseThrow(() -> new RuntimeException("Default post status not found"));

        PostEntity entity = new PostEntity();
        entity.setTitle(dto.title());
        entity.setContent(dto.content());
        entity.setCreatedTime(LocalDateTime.now());
        entity.setUser(user);
        entity.setStatus(defaultStatus);

        PostEntity saved = postRepository.save(entity);

        return mapToDto(saved);
    }

    public Optional<PostEntityDto> update(Long id, CreatePostEntityDto dto) {
        LOGGER.info("Updating post with id={}", id);
        return postRepository.findById(id).map(existing -> {

            existing.setTitle(dto.title());
            existing.setContent(dto.content());
            existing.setUpdatedTime(LocalDateTime.now());

            postRepository.save(existing);

            return mapToDto(existing);
        });
    }

    public PostEntityDto changeStatus(Long postId, PostStatusEnum newStatus) {

        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        PostStatusEntity status = postStatusRepository.findById(newStatus.name())
                .orElseThrow(() -> new RuntimeException("Status not found"));

        post.setStatus(status);
        post.setUpdatedTime(LocalDateTime.now());

        postRepository.save(post);

        return mapToDto(post);
    }

    public boolean delete(Long id) {
        LOGGER.info("Deleting post with id={}", id);
        if (!postRepository.existsById(id)) {
            LOGGER.info("Post not found id={}", id);
            return false;
        }
        postRepository.deleteById(id);
        return true;
    }

    private PostEntityDto mapToDto(PostEntity entity) {
        return new PostEntityDto(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getFormattedCreateTime(),
                entity.getUser().getId()
        );
    }
}