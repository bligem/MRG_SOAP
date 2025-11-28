package com.soap.api.service;

import com.soap.api.dto.*;
import com.soap.api.db.User;
import com.soap.api.db.UserRepository;
import com.soap.api.request.user.LoginRequest;
import com.soap.api.request.user.RegisterRequest;
import com.soap.api.request.user.UpdateUserRequest;
import com.soap.api.response.DeleteUserResponse;
import com.soap.api.response.LoginResponse;
import com.soap.api.util.JwtUtil;
import com.soap.api.util.RoleUtils;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.*;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository repo, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    public UserDto register(RegisterRequest req) {
        String email = req.getEmail().toLowerCase(Locale.ROOT).trim();
        repo.findByEmail(email).ifPresent(u -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        });

        User u = new User();
        u.setId(UUID.randomUUID());
        u.setEmail(email);
        u.setName(req.getName());
        u.setPasswordHash(encoder.encode(req.getPassword()));
        u.setRoles(new ArrayList<>(List.of(Role.USER)));
        u.setCreatedAt(OffsetDateTime.now());
        u.setUpdatedAt(OffsetDateTime.now());
        u = repo.save(u);
        return UserDto.fromEntity(u);
    }

    public LoginResponse loginAndBuildResponse(LoginRequest req) {
        String email = req.getEmail().toLowerCase(Locale.ROOT).trim();
        User user = repo.findByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
        if (!encoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        String token = jwtUtil.generateToken(user.getId(), user.getRoles());
        return new LoginResponse(token, UserDto.fromEntity(user));
    }

    @Transactional
    public DeleteUserResponse deleteUser(UUID targetId, List<Role> callerRoles) {
        boolean callerIsAdmin = RoleUtils.hasRole(callerRoles, Role.ADMIN);
        if (!callerIsAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admin can delete users");
        }
        if (targetId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing user id");
        }
        boolean exists = repo.existsById(targetId);
        if (!exists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        repo.deleteById(targetId);
        return new DeleteUserResponse(true, targetId);
    }

    public UserDto update(UpdateUserRequest req, UUID callerId, List<Role> callerRoles) {
        UUID targetId = getUuid(req, callerId, callerRoles);
        User user = repo.findById(targetId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (req.getEmail() != null) {
            String normalized = req.getEmail().toLowerCase(Locale.ROOT).trim();
            User finalUser = user;
            repo.findByEmail(normalized).ifPresent(ex -> {
                if (!ex.getId().equals(finalUser.getId())) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
                }
            });
            user.setEmail(normalized);
        }
        if (req.getName() != null) user.setName(req.getName());
        if (req.getPassword() != null) user.setPasswordHash(encoder.encode(req.getPassword()));

        if (req.getRoles() != null) {
            boolean callerIsAdmin = callerRoles != null && callerRoles.contains(Role.ADMIN);
            if (!callerIsAdmin) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admin can change roles");
            }

            List<Role> newRoles = RoleUtils.toRoleList(req.getRoles());
            if (newRoles.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Roles must contain at least one role");
            }
            user.setRoles(new ArrayList<>(newRoles));
        }

        user.setUpdatedAt(OffsetDateTime.now());
        user = repo.save(user);
        return UserDto.fromEntity(user);
    }

    private static UUID getUuid(UpdateUserRequest req, UUID callerId, List<Role> callerRoles) {
        boolean isOnlyUser = RoleUtils.hasRole(callerRoles, Role.USER) && !RoleUtils.hasRole(callerRoles, Role.ADMIN);
        UUID targetId = req.getId();
        if (isOnlyUser) {
            if (targetId != null && !callerId.equals(targetId)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
            }
            targetId = callerId;
        } else {
            if (targetId == null) targetId = callerId;
        }
        if (targetId == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing id");
        return targetId;
    }

    public Optional<UserDto> getUserDto(UUID id, TokenDto caller) {
        boolean isAdmin = caller.getRoles() != null && caller.getRoles().contains(Role.ADMIN);
        if (!isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admin can access this operation");
        }
        return repo.findById(id).map(UserDto::fromEntity);
    }

    public List<UserDto> listUsers(Integer limit) {
        int max = 10;
        if (limit != null && limit >= 1 && limit <= 50) max = limit;
        List<UserDto> list = new ArrayList<>();
        repo.findAll().stream().limit(max).forEach(u -> list.add(UserDto.fromEntity(u)));
        return list;
    }
}
