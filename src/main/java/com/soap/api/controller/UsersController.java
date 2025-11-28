package com.soap.api.controller;

import com.soap.api.dto.TokenDto;
import com.soap.api.dto.UserDto;
import com.soap.api.request.user.ListUsersRequest;
import com.soap.api.request.user.UpdateUserRequest;
import com.soap.api.request.user.UserRequest;
import com.soap.api.response.DeleteUserResponse;
import com.soap.api.service.UserService;
import com.soap.api.util.JwtUtil;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@WebService(
        serviceName = "UsersService",
        portName = "UsersServicePort",
        targetNamespace = "http://soap.api.com/"
)
@Service
@RequiredArgsConstructor
public class UsersController {
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @WebMethod
    public List<UserDto> usersList(
            @WebParam(name = "ListUsers") ListUsersRequest req){
        return userService.listUsers(req.getLimit());
    }
}
