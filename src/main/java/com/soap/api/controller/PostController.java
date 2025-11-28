package com.soap.api.controller;

import com.soap.api.dto.PostDto;
import com.soap.api.dto.Role;
import com.soap.api.dto.TokenDto;
import com.soap.api.request.post.*;
import com.soap.api.response.DeletePostResponse;
import com.soap.api.response.ListPostsResponse;
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
        serviceName = "PostService",
        portName = "PostServicePort",
        targetNamespace = "http://soap.api.com/"
)
@Service
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtUtil jwtUtil;

    @WebMethod
    public PostDto createPost(
            @WebParam(name = "Authorization", header = true) String authHeader,
            @WebParam(name = "CreatePost") CreatePostRequest req) {

        TokenDto caller = jwtUtil.parseAuthHeader(authHeader);
        return postService.create(req, caller.getUserId());
    }

    @WebMethod
    public ListPostsResponse listPosts(
            @WebParam(name = "Authorization", header = true) String authHeader,
            @WebParam(name = "ListPosts") ListPostsRequest req) {

        TokenDto caller = authHeader != null ? jwtUtil.parseAuthHeader(authHeader) : null;
        UUID callerId = caller != null ? caller.getUserId() : null;
        List<Role> roles = caller != null ? caller.getRoles() : List.of();
        List<PostDto> posts = postService.listPosts(req, callerId, roles);
        return new ListPostsResponse(posts);
    }

    @WebMethod
    public PostDto updatePost(
            @WebParam(name = "Authorization", header = true) String authHeader,
            @WebParam(name = "UpdatePost") UpdatePostRequest req) {
        TokenDto caller = jwtUtil.parseAuthHeader(authHeader);
        UUID callerId = caller.getUserId();
        List<Role> roles = caller.getRoles();
        return postService.update(req, callerId, roles);
    }

    @WebMethod
    public DeletePostResponse deletePost(
            @WebParam(name = "Authorization", header = true) String authHeader,
            @WebParam(name = "DeletePost") DeletePostRequest req) {
        TokenDto caller = jwtUtil.parseAuthHeader(authHeader);
        UUID callerId = caller.getUserId();
        List<Role> roles = caller.getRoles();
        postService.delete(req, callerId, roles);
        return new DeletePostResponse(true, req.getId());
    }
}
