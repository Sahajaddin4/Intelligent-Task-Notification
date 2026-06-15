package org.taskmanagement.authservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.taskmanagement.authservice.entity.Auth;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth,Long> {
    Optional<Auth> findByEmail(String email);
    Boolean existsByEmail(String email);
    Optional<Auth> findByEmailAndPassword(String email, String password);
}
