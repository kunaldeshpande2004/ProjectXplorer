package com.Batch_1.projectXplorer.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class PropyService {

    @Value("${propy.url}")
    private String propyUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> askPropy(String query) {

        String url = propyUrl + "/api/ask";

        Map<String, String> request = new HashMap<>();
        request.put("query", query);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                Map.class
        );

        return response.getBody();
    }
}