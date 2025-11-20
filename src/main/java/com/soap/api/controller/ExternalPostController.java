package com.soap.api.controller;

import com.soap.api.dto.ExternalPostDto;
import com.soap.api.request.ExternalPostRequest;
import com.soap.api.service.ExternalPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/external", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
@RequiredArgsConstructor
public class ExternalPostController {

    private final ExternalPostService externalPostService;

    /**
     * Fetch posts from JSONPlaceholder
     * @param request contains limit field in XML body
     * @return list of posts
     */
    @GetMapping("/posts")
    public List<ExternalPostDto> getExternalPosts(@Valid @RequestBody ExternalPostRequest request) {
        return externalPostService.fetchPosts(request.getLimit());
    }
}
