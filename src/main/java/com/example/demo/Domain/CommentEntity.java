package com.example.demo.Domain;

import jakarta.persistence.*;
import liquibase.license.User;

import java.time.format.DateTimeFormatter;

@Entity
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private DateTimeFormatter create_time;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private PostEntity post;
}