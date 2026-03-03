package com.example.demo.repository;

import com.example.demo.domain.PostStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostStatusRepository extends JpaRepository<PostStatusEntity, String> {
}