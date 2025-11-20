package com.soap.api.controller;

import com.soap.api.dto.TokenDto;
import com.soap.api.dto.UserDto;
import com.soap.api.request.user.ListUsersRequest;
import com.soap.api.request.user.UpdateUserRequest;
import com.soap.api.request.user.UserRequest;
import com.soap.api.response.DeleteUserResponse;
import com.soap.api.service.UserService;
import com.soap.api.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping(path = "/users", produces = "application/xml")
    public List<UserDto> usersList(@Valid @RequestBody ListUsersRequest req){
        return userService.listUsers(req.getLimit());
    }
    @GetMapping(path = "/user", produces = "application/xml")
    public Optional<UserDto> listUser(@Valid @RequestBody UserRequest req){
        return userService.getUserDto(req.getId());
    }
    @PutMapping(path = "/user", produces = "application/xml")
    public UserDto updateUser(
            @RequestHeader(value = "Authorization", required = true) String authHeader,
            @Valid @RequestBody UpdateUserRequest req) {
        TokenDto caller = jwtUtil.parseAuthHeader(authHeader);
        return userService.update(req, caller.getUserId(), caller.getRoles());
    }
    @DeleteMapping(path = "/user", produces = "application/xml")
    public DeleteUserResponse deleteUser(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody UserRequest req) {
        TokenDto caller = jwtUtil.parseAuthHeader(authHeader);
        return userService.deleteUser(req.getId(), caller.getRoles());
    }
}
