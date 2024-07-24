package com.blog.blogspringboot.controller;

import com.blog.blogspringboot.repository.DatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DatabaseController {

    private final DatabaseService databaseService;

    @DeleteMapping("/nuke/db")
    public ResponseEntity<Object> restartDatabase() {
        try {
            databaseService.restartDatabase();
            return ResponseEntity.ok("Database has been restarted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to restart the database: " + e.getMessage());
        }
    }
}
