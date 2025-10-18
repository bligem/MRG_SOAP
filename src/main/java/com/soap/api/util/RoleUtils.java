package com.soap.api.util;

import com.soap.api.dto.Role;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.*;
import java.util.stream.Collectors;

public final class RoleUtils {
    private RoleUtils(){}

    public static List<Role> toRoleList(List<String> roles) {
        if (roles == null) return Collections.emptyList();
        EnumSet<Role> set = EnumSet.noneOf(Role.class);

        for (String r : roles) {
            if (r == null || r.isBlank()) continue;
            try {
                Role role = Role.valueOf(r.trim().toUpperCase(Locale.ROOT));
                set.add(role);
            } catch (IllegalArgumentException ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid role: " + r);
            }
        }
        return new ArrayList<>(set);
    }

    public static List<String> toStringList(Collection<Role> roles) {
        if (roles == null) return new ArrayList<>();
        return roles.stream().map(Role::name).collect(Collectors.toList());
    }
    public static boolean hasRole(Collection<Role> roles, Role role) {
        if (roles == null) return false;
        return roles.contains(role);
    }
}
