package com.fadeaqua.gambizzwebsite.domain;

import java.util.List;

public class RazedLeaderboardResponse {

    private String from;
    private String to;
    private List<RazedLeaderboardEntry> data; // Match the "data" field in the response

    // Constructor
    public RazedLeaderboardResponse(String from, String to, List<RazedLeaderboardEntry> data) {
        this.from = from;
        this.to = to;
        this.data = data;
    }

    // Getters
    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public List<RazedLeaderboardEntry> getData() {
        return data;
    }
}
