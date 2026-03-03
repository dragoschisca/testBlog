package com.example.demo.domain;
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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}