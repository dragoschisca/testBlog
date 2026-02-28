package com.example.demo.Domain;
import jakarta.persistence.*;

@Entity
@Table(name = "post_status")
public class PostStatusEntity {

    @Id
    @Column(name = "status", nullable = false, length = 50)
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}