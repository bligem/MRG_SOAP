package com.soap.api.dto;

public enum Role {
    ADMIN,
    USER,
    MODERATOR;

    public static Role fromString(String s) {
        if (s == null) throw new IllegalArgumentException("role is null");
        return Role.valueOf(s.trim().toUpperCase());
    }
}