package com.blog.blogspringboot.controller;

import com.blog.blogspringboot.repository.DatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequiredArgsConstructor
public class DatabaseController {

    private final DatabaseService databaseService;

    @Operation(summary = "Restart the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Database restarted successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(value = "{ \"success\": true, \"message\": \"Database has been restarted successfully.\" }"))
                    }),
            @ApiResponse(responseCode = "500", description = "Failed to restart the database",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(value = "{ \"success\": false, \"message\": \"Failed to restart the database: {error_message}\" }"))
                    })
    })
    @DeleteMapping("/nuke/db")
    public ResponseEntity<Object> restartDatabase() {
        databaseService.restartDatabase();
        return ResponseEntity.ok("Database has been restarted successfully.");
    }
}
