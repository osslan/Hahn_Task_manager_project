package com.example.backend.mapper;

import com.example.backend.dto.TaskDTO;
import com.example.backend.model.Task;

public class TaskMapper {
    public static TaskDTO toDTO(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .deadline(task.getDeadline())
                .completed(task.getCompleted())
                .projectId(task.getProject().getId())
                .build();
    }


    public static Task toModel(TaskDTO dto) {
        return Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .deadline(dto.getDeadline())
                .completed(dto.getCompleted())
                .build();
    }
}
