package com.blog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Component
@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/readiness")
    public ResponseEntity<Map<String, String>> readiness() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "READY");
        response.put("message", "Health check endpoint");
        return ResponseEntity.ok(response);
    }
}