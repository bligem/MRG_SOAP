package com.soap.api.controller;

import com.soap.api.dto.*;
import com.soap.api.request.user.RegisterRequest;
import com.soap.api.service.UserService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@WebService(
        serviceName = "RegisterService",
        portName = "RegisterServicePort",
        targetNamespace = "http://soap.api.com/"
)
@Service
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @WebMethod
    public UserDto register(@WebParam(name = "RegisterRequest") RegisterRequest req) {
        return userService.register(req);
    }


}