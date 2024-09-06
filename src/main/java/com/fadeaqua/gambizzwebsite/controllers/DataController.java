package com.fadeaqua.gambizzwebsite.controllers;

import com.fadeaqua.gambizzwebsite.domain.LeaderboardEntry;
import com.fadeaqua.gambizzwebsite.services.DataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/data")
    public ResponseEntity<List<LeaderboardEntry>> getData() {
        List<LeaderboardEntry> entries = dataService.fetchData();
        return ResponseEntity.ok(entries);
    }
}
