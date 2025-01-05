package com.fadeaqua.gambizzwebsite.services;

import com.fadeaqua.gambizzwebsite.domain.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DataService {

    private final RestTemplate restTemplate;

    public DataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<RazedLeaderboardEntry> fetchRazedLeaderboard() {
        // Date formatter for "YYYY-MM-DD HH:MM:SS"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate now = LocalDate.now();

        String fromDate = now.withDayOfMonth(1).atStartOfDay().format(formatter);
        String toDate = now.withDayOfMonth(now.lengthOfMonth()).atTime(23, 59, 59).format(formatter);

        // Build URL with constant query parameters
        String url = String.format(
                "%s?referral_code=%s&from=%s&to=%s&top=%d",
                "https://api.razed.com/player/api/v1/referrals/leaderboard", "TGZZ", fromDate, toDate, 10
        );

        // Set headers and make the request
        var headers = new HttpHeaders();
        headers.set("X-Referral-Key", "eyJpdiI6Im1CbkVxc3ZFRmxuOURKNG9aTjF1U0E9PSIsInZhbHVlIjoiTXRiaTJ1Sk1kSGg0OEVkemtXSzh4UT09IiwibWFjIjoiOGRiMjY2ZDlmYWFhYmQxMzM3ODYzNmM0NzJjYmY3ZjA3OWUzYmFjMjA3OTI2MWQ1YmI0NmY5ZjZmNTczZGYyMiIsInRhZyI6IiJ9");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<RazedLeaderboardResponse> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, RazedLeaderboardResponse.class);

        return response.getBody() != null ? response.getBody().getData() : List.of();
    }
}
