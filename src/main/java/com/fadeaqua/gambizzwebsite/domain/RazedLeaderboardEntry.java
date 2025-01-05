package com.fadeaqua.gambizzwebsite.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RazedLeaderboardEntry {

    private String username;  // Maps to "username" from API
    private double wagered;  // Maps to "wagered" from API

    // No-argument constructor for deserialization
    public RazedLeaderboardEntry() {
    }

    // Constructor using API data fields
    public RazedLeaderboardEntry(String username, double wagered) {
        this.username = username;
        this.wagered = wagered;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public double getWagered() {
        return wagered;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setWagered(double wagered) {
        BigDecimal bigDecimal = new BigDecimal(wagered);
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        this.wagered = bigDecimal.doubleValue();
    }
}
