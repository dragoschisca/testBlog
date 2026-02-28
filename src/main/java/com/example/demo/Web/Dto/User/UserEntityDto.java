package com.example.demo.Web.Dto.User;

import com.example.demo.Domain.UserEntity;

public class UserEntityDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    public UserEntityDto(Long id, String username, String firstName, String lastName, String email) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public static UserEntityDto fromEntity(UserEntity entity) {
        return new UserEntityDto(
                entity.getId(),
                entity.getUsername(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail()
        );
    }

}