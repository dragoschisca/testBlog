package com.example.demo.repository;

import com.example.demo.domain.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Page<CommentEntity> findByPostId(Long postId, Pageable pageable);
    Page<CommentEntity> findByUserId(Long userId, Pageable pageable);
}

