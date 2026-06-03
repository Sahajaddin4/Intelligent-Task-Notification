package org.taskmanagement.authservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.taskmanagement.authservice.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String tokenValue);
}
