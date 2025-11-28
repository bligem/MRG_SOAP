package com.soap.api.controller;

import com.soap.api.request.user.LoginRequest;
import com.soap.api.response.LoginResponse;
import com.soap.api.service.UserService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@WebService(
        serviceName = "LoginService",
        portName = "LoginServicePort",
        targetNamespace = "http://soap.api.com/"
)
@Service
@RequiredArgsConstructor
public class LoginController{

    private final UserService userService;

    @WebMethod
    public LoginResponse login(@WebParam(name = "LoginRequest")  LoginRequest req) {
        return userService.loginAndBuildResponse(req);
    }

}
