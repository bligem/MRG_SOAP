package com.soap.api.service;

import com.soap.api.dto.ExternalPostDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExternalPostService {

    private static final URI BASE_URL = URI.create("https://jsonplaceholder.typicode.com/posts");
    private final RestTemplate restTemplate;

    public ExternalPostService() {
        this.restTemplate = new RestTemplate();
        this.restTemplate.setRequestFactory(clientHttpRequestFactory());
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        return new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                super.prepareConnection(connection, httpMethod);
                connection.setInstanceFollowRedirects(false);
                setConnectTimeout(3000);
                setReadTimeout(5000);
            }
        };
    }


    public List<ExternalPostDto> fetchPosts(Integer limit) {
        try {
            limit = parseLimit(limit);
            URI uri = UriComponentsBuilder.fromUri(BASE_URL).build().toUri();
            ExternalPostDto[] response = restTemplate.getForObject(uri, ExternalPostDto[].class);

            return Arrays.stream(response)
                    .limit(parseLimit(limit))
                    .filter(p -> p.getId() != null && p.getTitle() != null && !p.getTitle().isBlank())
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
    private int parseLimit(Integer limit) {
        int l = 10;
        if (limit != null && limit >= 1 && limit <= 50) l = limit;
        return l;
    }
}
