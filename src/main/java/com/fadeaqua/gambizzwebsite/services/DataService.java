package com.fadeaqua.gambizzwebsite.services;

import com.fadeaqua.gambizzwebsite.domain.LeaderboardEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DataService {

    @Value("${external.api.url}")
    private String externalApiUrl;

    private final RestTemplate restTemplate;

    public DataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<LeaderboardEntry> fetchData() {
        // Fetch the data from the external API
        LeaderboardEntry[] entries = restTemplate.getForObject(externalApiUrl, LeaderboardEntry[].class);
        return List.of(entries);
    }
}
