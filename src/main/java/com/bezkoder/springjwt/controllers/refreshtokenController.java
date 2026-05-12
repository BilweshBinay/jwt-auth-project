package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.RefreshToken;
import com.bezkoder.springjwt.payload.request.TokenRefreshRequest;
import com.bezkoder.springjwt.payload.response.TokenRefreshResponse;
import com.bezkoder.springjwt.security.services.RefreshTokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

import static jdk.internal.org.jline.keymap.KeyMap.key;

public class refreshtokenController {
    @PostMapping("/refreshtoken")

    @Autowired
    RefreshTokenService refreshTokenService;


    public ResponseEntity<?> refreshtoken(
            @RequestBody TokenRefreshRequest request) {

        String requestRefreshToken = request.getToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {

                    String token =
                            jwtUtils.generateTokenFromUsername(user.getUsername());

                    TokenRefreshResponse response =
                            new TokenRefreshResponse();

                    response.setAccessToken(token);
                    response.setRefreshToken(requestRefreshToken);

                    return ResponseEntity.ok(response);
                })
                .orElseThrow(() ->
                        new RuntimeException(
                                "Refresh token is not in database"));
    }

    public String generateTokenFromUsername(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(
                                (new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
}
