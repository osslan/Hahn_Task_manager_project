package com.example.backend.service;

import com.example.backend.dto.TaskDTO;
import com.example.backend.exception.ProjectNotFoundException;
import com.example.backend.exception.TaskNotFoundException;
import com.example.backend.mapper.TaskMapper;
import com.example.backend.model.Project;
import com.example.backend.model.Task;
import com.example.backend.repository.ProjectRepository;
import com.example.backend.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public Page<TaskDTO> getTasksByProjectId(Long projectId, int size, int page) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id : " + projectId));

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        return taskRepository.findByProjectId(project.getId(), pageable)
                .map(TaskMapper::toDTO);
    }

    public TaskDTO addTask(TaskDTO taskDTO) {
        Project project = projectRepository.findById(taskDTO.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id : " + taskDTO.getProjectId()));

        Task task = TaskMapper.toModel(taskDTO);
        task.setProject(project);
        Task newTask = taskRepository.save(task);

        return TaskMapper.toDTO(newTask);
    }

    public void deleteById(Long taskId) {
        boolean taskExists = taskRepository.existsById(taskId);

        if (taskExists) {
            taskRepository.deleteById(taskId);
        } else {
            throw new TaskNotFoundException("Task with id : " + taskId + " not found");
        }
    }

    public TaskDTO updateTask(TaskDTO taskDTO) {
        Task task = taskRepository.findById(taskDTO.getId())
                .orElseThrow(() -> new TaskNotFoundException("Task with id : " + taskDTO.getId()));

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDeadline(taskDTO.getDeadline());
        task.setCompleted(taskDTO.getCompleted());
        Task updatedTask = taskRepository.save(task);

        return TaskMapper.toDTO(taskRepository.save(updatedTask));
    }

}


























