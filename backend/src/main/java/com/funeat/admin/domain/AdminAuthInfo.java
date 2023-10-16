package com.funeat.admin.domain;

public class AdminAuthInfo {

    private final String id;
    private final String key;

    public AdminAuthInfo(final String id, final String key) {
        this.id = id;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }
}
