package com.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/db-test")
public class DatabaseTestController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/connection")
    public ResponseEntity<Map<String, String>> testConnection() {
        Map<String, String> response = new HashMap<>();

        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(5)) {
                response.put("status", "SUCCESS");
                response.put("message", "Database connection successful");
                response.put("database", connection.getCatalog());
            } else {
                response.put("status", "ERROR");
                response.put("message", "Database connection is not valid");
            }
        } catch (SQLException e) {
            response.put("status", "ERROR");
            response.put("message", "Database connection failed: " + e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/config")
    public ResponseEntity<Map<String, String>> showConfig() {
        Map<String, String> response = new HashMap<>();

        try {
            response.put("url", dataSource.getConnection().getMetaData().getURL());
            response.put("username", dataSource.getConnection().getMetaData().getUserName());
            response.put("driver", dataSource.getConnection().getMetaData().getDriverName());
            response.put("status", "SUCCESS");
        } catch (SQLException e) {
            response.put("status", "ERROR");
            response.put("message", "Failed to get database config: " + e.getMessage());
        }

        return ResponseEntity.ok(response);
    }
}