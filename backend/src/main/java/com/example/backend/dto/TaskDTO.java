package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TaskDTO {

    private Long id;
    private String title;
    private String description;
    private LocalDate deadline;
    private Boolean completed;
    private Long projectId;
}
