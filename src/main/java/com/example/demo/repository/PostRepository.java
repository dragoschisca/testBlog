package com.example.demo.repository;

import com.example.demo.domain.PostEntity;
import com.example.demo.domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Page<PostEntity> findAllByUserId(UserEntity user, Pageable pageable);
    Page<PostEntity> findAll(Pageable pageable);

}

