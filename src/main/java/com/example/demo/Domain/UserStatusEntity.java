package com.example.demo.Domain;
import jakarta.persistence.*;

@Entity
@Table(name = "user_status")
public class UserStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}