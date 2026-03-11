package com.suguiura.user.infrastructure.repository;

import com.suguiura.user.infrastructure.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

    @Transactional //Serve para ajudar a não dar erro na hora de deletar
    void deleteByEmail(String email);
}
