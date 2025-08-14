package com.TechnicalTest.list_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import com.TechnicalTest.list_service.config.RestTemplateConfig.*;
import org.springframework.web.client.RestTemplate;


@Service
public class PagerDutyService {


    private final RestTemplate restTemplate;

    @Autowired
    public PagerDutyService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Value("${pager.api.key}")
    private String apiKey;

    @Value("${pager.endpoint.url}")
    private String endpointUrl;

    public String getServices() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Token token=" + apiKey);
        headers.set("Accept", "application/vnd.pagerduty+json;version=2");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("From", "carlos.correa.zapata@gmail.com");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                endpointUrl,
                HttpMethod.GET,
                entity,
                String.class
        );

        return response.getBody();
    }
}
