package com.example.demo.repository;

import com.example.demo.domain.UserStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserStatusRepository extends JpaRepository<UserStatusEntity, String> {
    @Override
    Optional<UserStatusEntity> findById(String id);

}