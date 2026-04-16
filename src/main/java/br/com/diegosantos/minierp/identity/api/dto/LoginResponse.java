package br.com.diegosantos.minierp.identity.api.dto;

import java.time.OffsetDateTime;

public class LoginResponse {

    private String token;
    private String type;
    private OffsetDateTime expiresAt;

    public LoginResponse() {
    }

    public LoginResponse(String token, String type, OffsetDateTime expiresAt) {
        this.token = token;
        this.type = type;
        this.expiresAt = expiresAt;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public OffsetDateTime getExpiresAt() {
        return expiresAt;
    }
}