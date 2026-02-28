package com.example.demo.Repository;

import com.example.demo.Domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
    @Query(
            value = "select * from user_entity where email=:email",
            nativeQuery = true
    )

    Optional<UserEntity> findByEmailQuery(String email);
//
//    @Query(
//            value = "select first_name, last_name  from user_entity where email=:email",
//            nativeQuery = true
//    )
