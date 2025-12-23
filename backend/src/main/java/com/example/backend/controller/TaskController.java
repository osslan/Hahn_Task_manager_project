package com.example.backend.controller;

import com.example.backend.dto.PageResponse;
import com.example.backend.dto.TaskDTO;
import com.example.backend.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "API endpoints for managing tasks")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Operation(
            summary = "Create a new task",
            description = "Create a new task for a project"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<TaskDTO> createTask(
            @Parameter(description = "Task details", required = true)
            @RequestBody TaskDTO taskDTO
    ) {
        TaskDTO newTask = taskService.addTask(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

    @GetMapping("/project/{projectId}")
    @Operation(
            summary = "Get tasks by project ID",
            description = "Retrieve paginated list of tasks for a specific project"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<PageResponse<TaskDTO>> getTasks(
            @Parameter(description = "Project ID", required = true, example = "1")
            @PathVariable Long projectId,
            @Parameter(description = "Number of items per page", required = true, example = "10")
            @RequestParam int size,
            @Parameter(description = "Page number (0-indexed)", required = true, example = "0")
            @RequestParam int page
    ) {
        Page<TaskDTO> result = taskService.getTasksByProjectId(projectId, size, page);

        PageResponse<TaskDTO> response = new PageResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a task",
            description = "Delete a task by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<Void> deleteTask(
            @Parameter(description = "Task ID", required = true, example = "1")
            @PathVariable Long id
    ) {
        taskService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation(
            summary = "Update a task",
            description = "Update an existing task with new details"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<TaskDTO> updateTask(
            @Parameter(description = "Updated task details", required = true)
            @RequestBody TaskDTO taskDTO
    ) {
        TaskDTO updatedTask = taskService.updateTask(taskDTO);
        return ResponseEntity.ok(updatedTask);
    }

}
