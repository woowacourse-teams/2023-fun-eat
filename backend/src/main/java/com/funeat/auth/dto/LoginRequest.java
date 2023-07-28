package com.funeat.auth.dto;

public class LoginRequest {

    private final Long id;

    public LoginRequest(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
