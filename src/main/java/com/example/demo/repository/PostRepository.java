package com.example.demo.Repository;

import com.example.demo.Domain.PostEntity;
import com.example.demo.Domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Page<PostEntity> findAllByUserId(UserEntity user, Pageable pageable);
    Page<PostEntity> findAll(Pageable pageable);

}

