package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.RefreshToken;
import com.bezkoder.springjwt.payload.request.TokenRefreshRequest;
import com.bezkoder.springjwt.payload.response.TokenRefreshResponse;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.security.services.RefreshTokenService;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

//import static jdk.internal.org.jline.keymap.KeyMap.key;

@RestController
@RequestMapping("/api/auth")
public class refreshtokenController {

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@RequestBody TokenRefreshRequest request) {

        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {

                    String newAccessToken =
                            jwtUtils.generateTokenFromUsername(user.getUsername());

                    TokenRefreshResponse response =
                            new TokenRefreshResponse(
                                    newAccessToken,
                                    requestRefreshToken
                            );

                    return ResponseEntity.ok(response);
                })
                .orElseThrow(() ->
                        new RuntimeException("Refresh token is not in database"));
    }

//    public String generateTokenFromUsername(String username) {
//
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(
//                        new Date(
//                                (new Date()).getTime() + jwtExpirationMs))
//                .signWith(key(), SignatureAlgorithm.HS256)
//                .compact();
//    }
}
