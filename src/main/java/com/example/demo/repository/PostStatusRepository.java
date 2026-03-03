package com.example.demo.Repository;

import com.example.demo.Domain.PostStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostStatusRepository extends JpaRepository<PostStatusEntity, String> {
}