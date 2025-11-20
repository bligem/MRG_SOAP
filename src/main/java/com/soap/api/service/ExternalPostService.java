package com.soap.api.service;

import com.soap.api.dto.ExternalPostDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExternalPostService {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/posts";
    private final RestTemplate restTemplate;

    public ExternalPostService() {
        this.restTemplate = new RestTemplate();
        this.restTemplate.setRequestFactory(clientHttpRequestFactory());
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        var factory = new org.springframework.http.client.SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(5000);
        return factory;
    }

    public List<ExternalPostDto> fetchPosts(Integer limit) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(BASE_URL).build().toUri();
            ExternalPostDto[] response = restTemplate.getForObject(uri, ExternalPostDto[].class);

            return Arrays.stream(response)
                    .filter(p -> p.getId() != null && p.getTitle() != null && !p.getTitle().isBlank())
                    .limit(limit != null && limit > 0 ? limit : 10)
                    .collect(Collectors.toList());

        } catch (HttpClientErrorException e) {
            log.error("Error fetching posts: {}", e.getStatusCode());
            throw new RuntimeException("Failed to fetch external posts", e);
        } catch (ResourceAccessException e) {
            log.error("Timeout or connection error: {}", e.getMessage());
            throw new RuntimeException("External API timeout or unreachable", e);
        } catch (Exception e) {
            log.error("Unknown error fetching posts: {}", e.getMessage());
            throw new RuntimeException("Unknown error fetching external posts", e);
        }
    }
}
