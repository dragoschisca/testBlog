package com.example.demo.Domain;

import jakarta.persistence.*;
import liquibase.license.User;

import java.time.format.DateTimeFormatter;

@Entity
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @Column(nullable = false)
    private DateTimeFormatter create_time;

    @Column(nullable = true)
    private DateTimeFormatter update_time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private PostStatusEntity status;
}