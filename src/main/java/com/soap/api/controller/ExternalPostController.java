package com.soap.api.controller;

import com.soap.api.dto.ExternalPostDto;
import com.soap.api.request.ExternalPostRequest;
import com.soap.api.service.ExternalPostService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@WebService(
        serviceName = "ExternalPostService",
        portName = "ExternalPostServicePort",
        targetNamespace = "http://soap.api.com/"
)
@Service
@RequiredArgsConstructor
public class ExternalPostController {

    private final ExternalPostService externalPostService;

    @WebMethod
    public List<ExternalPostDto> getExternalPosts(@WebParam(name = "request") ExternalPostRequest request) {
        return externalPostService.fetchPosts(request.getLimit());
    }
}
