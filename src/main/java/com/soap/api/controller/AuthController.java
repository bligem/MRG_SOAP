package com.soap.api.controller;

import com.soap.api.db.User;
import com.soap.api.dto.*;
import com.soap.api.request.user.LoginRequest;
import com.soap.api.request.user.RegisterRequest;
import com.soap.api.response.LoginResponse;
import com.soap.api.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
@AllArgsConstructor
public class AuthController {
    private UserService userService;

    @PostMapping(path = "/user/register")
    public UserDto register(@Valid @RequestBody RegisterRequest req) {
        return userService.register(req);
    }

    @PostMapping(path = "/user/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest req) {
        return userService.loginAndBuildResponse(req);
    }
}