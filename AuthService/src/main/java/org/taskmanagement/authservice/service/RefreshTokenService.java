package org.taskmanagement.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.taskmanagement.authservice.Repository.AuthRepository;
import org.taskmanagement.authservice.Repository.RefreshTokenRepository;
import org.taskmanagement.authservice.entity.Auth;
import org.taskmanagement.authservice.entity.RefreshToken;
import org.taskmanagement.authservice.exception.custom.TokenExpiredException;
import org.taskmanagement.authservice.exception.custom.UserNotFoundException;

import java.sql.Ref;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final AuthRepository authRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private Auth fetchUserAuthentication(String email) {
        return authRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User not found with email: " + email));
    }

    private Auth fetchUserAuthentication(Long userId) {
        return authRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found with id: " + userId));
    }

    public String createRefreshToken(String email) {

        Auth auth = fetchUserAuthentication(email);

        RefreshToken token = new RefreshToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUserId(auth.getId());
        token.setExpiryDate(Instant.now().plusSeconds(7 * 24 * 60 * 60));

        refreshTokenRepository.save(token);

        return token.getToken();
    }

    public boolean validateRefreshToken(String tokenValue) {

        RefreshToken token =
                refreshTokenRepository.findByToken(tokenValue)
                        .orElseThrow(() -> new TokenExpiredException("Invalid token"));

        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new TokenExpiredException("Refresh token expired");
        }

        return true;
    }
}
