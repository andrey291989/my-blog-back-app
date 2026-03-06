package com.blog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/simple-test")
public class SimpleTestController {

    @GetMapping
    public ResponseEntity<Map<String, String>> simpleTest() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Simple test endpoint works!");
        response.put("status", "SUCCESS");
        return ResponseEntity.ok(response);
    }
}