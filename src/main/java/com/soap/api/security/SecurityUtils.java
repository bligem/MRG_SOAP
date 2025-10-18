package com.soap.api.security;

import com.soap.api.dto.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;
import java.util.stream.Collectors;

public final class SecurityUtils {

    public static Optional<UUID> getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal() == null) return Optional.empty();
        try {
            return Optional.of(UUID.fromString(auth.getPrincipal().toString()));
        } catch (IllegalArgumentException ex) {
            return Optional.empty();
        }
    }

    public static List<Role> getCurrentRoles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return List.of();
        return auth.getAuthorities().stream()
                .map(a -> a.getAuthority()) // "ROLE_ADMIN"
                .filter(Objects::nonNull)
                .map(s -> s.replaceFirst("^ROLE_", ""))
                .map(String::toUpperCase)
                .map(Role::valueOf)
                .collect(Collectors.toList());
    }
}
