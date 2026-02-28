package com.example.demo.Service;

import com.example.demo.Domain.UserEntity;
import com.example.demo.Domain.UserStatusEntity;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.UserStatusRepository;
import com.example.demo.Web.Dto.User.CreateUserEntityDto;
import com.example.demo.Web.Dto.User.UserEntityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    public UserService(UserRepository userRepository, UserStatusRepository userStatusRepository) {
        this.userRepository = userRepository;
        this.userStatusRepository = userStatusRepository;
    }

    public Page<UserEntity> findAll(Pageable pageable) {
        LOGGER.info("Fetching users page={} size={}", pageable.getPageNumber(), pageable.getPageSize());
        return userRepository.findAll(pageable);
    }

    public Optional<UserEntity> findById(Long id) {
        LOGGER.info("Fetching user by id={}", id);
        return userRepository.findById(id);
    }

    public Optional<UserEntityDto> findDtoById(Long id) {
        LOGGER.info("Fetching user by id={}", id);
        return userRepository.findDtoById(id);
    }

    public UserEntityDto create(CreateUserEntityDto user) {
        LOGGER.info("Creating user userName={}", user.username());
        UserEntity entity = new UserEntity();
        applyDto(entity, user);

        UserStatusEntity defaultStatus = userStatusRepository.findById("ACTIVE")
                .orElseThrow(() -> new RuntimeException("Default user status not found"));

        entity.setStatus(defaultStatus);

        UserEntity created = userRepository.save(entity);
        return userRepository.findDtoById(created.getId())
                .orElseThrow(() -> new IllegalStateException("Created user not found id=" + created.getId()));
    }

    public Optional<UserEntityDto> update(Long id, CreateUserEntityDto incoming) {
        LOGGER.info("Updating user id={}", id);
        return userRepository.findById(id).map(existing -> {
            applyDto(existing, incoming);
            userRepository.save(existing);
            return userRepository.findDtoById(id)
                    .orElseThrow(() -> new IllegalStateException("Updated user not found id=" + id));
        });
    }

    public Optional<UserEntityDto> patch(Long id, CreateUserEntityDto incoming) {
        LOGGER.info("Patching user id={}", id);
        return userRepository.findById(id).map(existing -> {
            applyPatch(existing, incoming);
            userRepository.save(existing);
            return userRepository.findDtoById(id)
                    .orElseThrow(() -> new IllegalStateException("Patched user not found id=" + id));
        });
    }

    public boolean delete(Long id) {
        LOGGER.info("Deleting user id={}", id);
        if (!userRepository.existsById(id)) {
            LOGGER.info("User not found id={}", id);
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    private static void applyDto(UserEntity entity, CreateUserEntityDto incoming) {
        entity.setUsername(incoming.username());
        entity.setFirstName(incoming.firstName());
        entity.setLastName(incoming.lastName());
        entity.setEmail(incoming.email());

        final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        entity.setPassword(passwordEncoder.encode(incoming.password()));
    }

    private static void applyPatch(UserEntity entity, CreateUserEntityDto incoming) {
        if (incoming.username() != null) {
            entity.setUsername(incoming.username());
        }
        if (incoming.firstName() != null) {
            entity.setFirstName(incoming.firstName());
        }
        if (incoming.lastName() != null) {
            entity.setLastName(incoming.lastName());
        }
        if (incoming.email() != null) {
            entity.setEmail(incoming.email());
        }
    }
    public Optional<UserEntity> findByEmailQuery(String email) {
        return userRepository.findByEmail(email);
    }
}