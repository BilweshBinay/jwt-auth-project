package com.bezkoder.springjwt.models;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table (name = "refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long userId;
    private String token;
    private Instant expiryDate;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiresDate) {
        this.expiryDate = expiresDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
