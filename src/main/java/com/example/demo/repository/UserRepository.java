package com.example.demo.Repository;

import com.example.demo.Domain.UserEntity;
import com.example.demo.Web.Dto.User.UserEntityDto;
import com.example.demo.Web.Dto.User.UserEntityProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Override
    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByEmail(String email);

    @Query("""
        SELECT new com.example.demo.Web.Dto.User.UserEntityDto(
            u.id,
            u.username,
            u.firstName,
            u.lastName,
            u.email
        )
        FROM UserEntity u
        """)
    Page<UserEntityDto> findAllDtos(Pageable pageable);

    @Query("""
        SELECT new com.example.demo.Web.Dto.User.UserEntityDto(
            u.id,
            u.username,
            u.firstName,
            u.lastName,
            u.email
        )
        FROM UserEntity u
        WHERE u.id = :id
        """)
    Optional<UserEntityDto> findDtoById(@Param("id") Long id);
}