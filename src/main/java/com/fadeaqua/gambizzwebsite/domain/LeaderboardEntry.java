package com.fadeaqua.gambizzwebsite.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LeaderboardEntry {

    private String username;
    private double bets;
    private String reportingDay;

    public LeaderboardEntry(String username, double bets, String reportingDay) {
        this.username = username;
        BigDecimal bigDecimal = new BigDecimal(bets);
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        this.bets = bigDecimal.doubleValue();
        this.reportingDay = reportingDay;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getBets() {
        return bets;
    }

    public void setWager(double bets) {
        this.bets = bets;
    }

    public String getReportingDay() {
        return reportingDay;
    }
}
