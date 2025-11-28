package com.soap.api.controller;

import com.soap.api.dto.TokenDto;
import com.soap.api.dto.UserDto;
import com.soap.api.request.user.UpdateUserRequest;
import com.soap.api.request.user.UserRequest;
import com.soap.api.response.DeleteUserResponse;
import com.soap.api.service.UserService;
import com.soap.api.util.JwtUtil;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@WebService(
        serviceName = "UserService",
        portName = "UserServicePort",
        targetNamespace = "http://soap.api.com/"
)
@Service
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;


    @WebMethod
    public Optional<UserDto> listUser(
            @WebParam(name = "Authorization", header = true) String authHeader,
            @WebParam(name = "ListUser") UserRequest req){
        TokenDto caller = jwtUtil.parseAuthHeader(authHeader);
        return userService.getUserDto(req.getId(), caller);
    }
    @WebMethod
    public UserDto updateUser(
            @WebParam(name = "Authorization", header = true) String authHeader,
            @WebParam(name = "UpdateUser") UpdateUserRequest req) {
        TokenDto caller = jwtUtil.parseAuthHeader(authHeader);
        return userService.update(req, caller.getUserId(), caller.getRoles());
    }
    @WebMethod
    public DeleteUserResponse deleteUser(
            @WebParam(name = "Authorization", header = true) String authHeader,
            @WebParam(name = "DeleteUser") UserRequest req) {
        TokenDto caller = jwtUtil.parseAuthHeader(authHeader);
        return userService.deleteUser(req.getId(), caller.getRoles());
    }
}
