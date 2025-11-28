package com.soap.api.controller;

import com.soap.api.dto.PostDto;
import com.soap.api.dto.Role;
import com.soap.api.dto.TokenDto;
import com.soap.api.request.post.*;
import com.soap.api.service.PostService;

import com.soap.api.util.JwtUtil;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@WebService(
        serviceName = "PostByUserService",
        portName = "PostByUserServicePort",
        targetNamespace = "http://soap.api.com/"
)
@Service
@RequiredArgsConstructor
public class PostByUserController {

    private final PostService postService;
    private final JwtUtil jwtUtil;

    @WebMethod
    public List<PostDto> listPostsByUser(
            @WebParam(name = "Authorization", header = true) String authHeader,
            @WebParam(name = "ListPostsByUserRequest") ListPostsByUserRequest req) {
        TokenDto caller = authHeader != null ? jwtUtil.parseAuthHeader(authHeader) : null;
        UUID callerId = caller != null ? caller.getUserId() : null;
        List<Role> roles = caller != null ? caller.getRoles() : List.of();
        return postService.listPostsByUser(req, callerId, roles);
    }
}
