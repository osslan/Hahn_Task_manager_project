package com.example.backend.mapper;

import com.example.backend.dto.ProjectDTO;
import com.example.backend.model.Project;

public class ProjectMapper {
    public static ProjectDTO toDto(Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .userId(project.getUser().getId())
                .build();
    }

    public static Project toModel(ProjectDTO projectDTO) {
        return Project.builder()
                .title(projectDTO.getTitle())
                .description(projectDTO.getDescription())
                .build();
    }
}
