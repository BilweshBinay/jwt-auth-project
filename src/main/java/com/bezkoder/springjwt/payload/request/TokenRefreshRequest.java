package com.bezkoder.springjwt.payload.request;

public class TokenRefreshRequest {
    private  String token;

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
