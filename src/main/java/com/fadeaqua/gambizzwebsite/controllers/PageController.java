package com.fadeaqua.gambizzwebsite.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.URI;

@Controller
public class PageController {

    @RequestMapping("/leaderboards")
    public ResponseEntity<Resource> showLeaderboards(HttpServletRequest request) throws IOException {
        Resource resource = new ClassPathResource("static/leaderboard.html");
        if (resource.exists()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "text/html");
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping("/home")
    public ResponseEntity<Resource> showHome(HttpServletRequest request) throws IOException {
        Resource resource = new ClassPathResource("static/index.html");
        if (resource.exists()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "text/html");
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping("/bonuses")
    public ResponseEntity<Void> showBonuses() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/index.html#sponsorships"));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @RequestMapping("/socials")
    public ResponseEntity<Resource> showSocials(HttpServletRequest request) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/index.html#stay-connected"));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
