package com.example.backend.controller;

import com.example.backend.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@Tag(name = "Analytics", description = "API endpoints for project analytics and statistics")
@SecurityRequirement(name = "bearerAuth")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/totalTasks/{projectId}")
    @Operation(
            summary = "Get total tasks count",
            description = "Retrieve the total number of tasks for a specific project"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Analytics retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<Map<String, Integer>> getAnalytics(
            @Parameter(description = "Project ID", required = true, example = "1")
            @PathVariable Long projectId
    ) {
        int totalTasks = analyticsService.getTotalTasksByProjectId(projectId);

        Map<String, Integer> map = new HashMap<>();
        map.put("totalTasks", totalTasks);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/totalCompletdTasks/{projectId}")
    @Operation(
            summary = "Get completed tasks count",
            description = "Retrieve the total number of completed tasks for a specific project"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Analytics retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<Map<String, Integer>> getAnalyticsCompleted(
            @Parameter(description = "Project ID", required = true, example = "1")
            @PathVariable Long projectId
    ) {
        int totalTasks = analyticsService.getTotalCompletedTasks(projectId);
        Map<String, Integer> map = new HashMap<>();
        map.put("totalTasks", totalTasks);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/progression/{projectId}")
    @Operation(
            summary = "Get project progression percentage",
            description = "Retrieve the completion percentage of a project based on completed tasks"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Progression retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<Map<String, Double>> getProgress(
            @Parameter(description = "Project ID", required = true, example = "1")
            @PathVariable Long projectId
    ) {
        double percentageProgression = analyticsService.getPercentageProgression(projectId);
        Map<String, Double> map = new HashMap<>();
        map.put("percentageProgression", percentageProgression);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
