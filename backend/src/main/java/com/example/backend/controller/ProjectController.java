package com.example.backend.controller;

import com.example.backend.dto.PageResponse;
import com.example.backend.dto.ProjectDTO;
import com.example.backend.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "Projects", description = "API endpoints for managing projects")
@SecurityRequirement(name = "bearerAuth")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/user/{username}")
    @Operation(
            summary = "Get projects by username",
            description = "Retrieve paginated list of projects for a specific user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<PageResponse<ProjectDTO>> getProjectsByUser(
            @Parameter(description = "Username of the project owner", required = true)
            @PathVariable String username,
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ProjectDTO> result = projectService.getByUser(username, size, page);

        PageResponse<ProjectDTO> response = new PageResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(
            summary = "Create a new project",
            description = "Create a new project with the provided details"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token")
    })
    public ResponseEntity<ProjectDTO> addProject(
            @Parameter(description = "Project details", required = true)
            @RequestBody @Valid ProjectDTO projectDTO
    ) {
        ProjectDTO createdProject = projectService.addProject(projectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a project",
            description = "Delete a project by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Project deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<Void> deleteProject(
            @Parameter(description = "Project ID", required = true, example = "1")
            @PathVariable Long id
    ) {
        projectService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Operation(
            summary = "Update a project",
            description = "Update an existing project with new details"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<ProjectDTO> updateProject(
            @Parameter(description = "Updated project details", required = true)
            @RequestBody @Valid ProjectDTO projectDTO
    ) {
        ProjectDTO updated = projectService.updateProject(projectDTO);
        return ResponseEntity.ok(updated);
    }


}
