package com.soap.api.controller;

import com.soap.api.dto.PostDto;
import com.soap.api.dto.Role;
import com.soap.api.dto.TokenDto;
import com.soap.api.request.post.*;
import com.soap.api.response.DeleteUserResponse;
import com.soap.api.service.PostService;
import com.soap.api.util.JwtUtil;
import com.soap.api.util.RoleUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtUtil jwtUtil;

    @PostMapping(path = "/posts")
    public PostDto createPost(@RequestHeader("Authorization") String authHeader,
                              @Valid @RequestBody CreatePostRequest req) {
        TokenDto caller = jwtUtil.parseAuthHeader(authHeader);
        // TokenDto assumed to expose getUserId() and getRoles() returning List<Role>
        return postService.create(req, caller.getUserId());
    }

    @GetMapping(path = "/posts")
    public List<PostDto> listPosts(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                   @Valid @RequestBody(required = false) ListPostsRequest req) {
        TokenDto caller = authHeader != null ? jwtUtil.parseAuthHeader(authHeader) : null;
        UUID callerId = caller != null ? caller.getUserId() : null;
        List<Role> roles = caller != null ? caller.getRoles() : List.of();
        return postService.listPosts(req, callerId, roles);
    }

    @GetMapping(path = "/posts/byUser")
    public List<PostDto> listPostsByUser(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                         @Valid @RequestBody(required = false) ListPostsByUserRequest req) {
        TokenDto caller = authHeader != null ? jwtUtil.parseAuthHeader(authHeader) : null;
        UUID callerId = caller != null ? caller.getUserId() : null;
        List<Role> roles = caller != null ? caller.getRoles() : List.of();
        return postService.listPostsByUser(req, callerId, roles);
    }

    @PutMapping(path = "/posts")
    public PostDto updatePost(@RequestHeader("Authorization") String authHeader,
                              @Valid @RequestBody UpdatePostRequest req) {
        TokenDto caller = jwtUtil.parseAuthHeader(authHeader);
        UUID callerId = caller.getUserId();
        List<Role> roles = caller.getRoles();
        return postService.update(req, callerId, roles);
    }

    @DeleteMapping(path = "/posts")
    public com.soap.api.response.DeleteUserResponse deletePost(@RequestHeader("Authorization") String authHeader,
                                                               @Valid @RequestBody DeletePostRequest req) {
        TokenDto caller = jwtUtil.parseAuthHeader(authHeader);
        UUID callerId = caller.getUserId();
        List<Role> roles = caller.getRoles();
        postService.delete(req, callerId, roles);
        // reuse simple response DTO (deleted + id) or create dedicated one for posts
        return new com.soap.api.response.DeleteUserResponse(true, req.getId());
    }
}
