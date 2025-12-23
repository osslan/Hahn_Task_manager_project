package com.example.backend.service;

import com.example.backend.repository.ProjectRepository;
import com.example.backend.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;


    public int getTotalTasksByProjectId(Long projectId) {
        return taskRepository.countByProjectId(projectId);
    }

    public int getTotalCompletedTasks(Long projectId) {
        return taskRepository.countTasksByProjectIdAndCompletedTrue(projectId);
    }

    public double getPercentageProgression(Long projectId) {
        return (double) this.getTotalCompletedTasks(projectId) / this.getTotalTasksByProjectId(projectId);
    }

}
