package com.example.demo.Domain;
import jakarta.persistence.*;

@Entity
@Table(name = "post_status")
public class PostStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}