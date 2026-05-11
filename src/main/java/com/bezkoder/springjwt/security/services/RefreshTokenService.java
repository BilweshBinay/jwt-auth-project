package com.bezkoder.springjwt.security.services;

import com.bezkoder.springjwt.models.RefreshToken;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.RefreshTokenRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("{bezkoder.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    public RefreshToken createRefreshToken(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));

        refreshToken.setUser(user);
        return refreshTokenRepository.save(refreshToken);
    }
}
