package com.example.backend.service;

import com.example.backend.dto.ProjectDTO;
import com.example.backend.exception.ProjectNotFoundException;
import com.example.backend.exception.UsernameNotFoundException;
import com.example.backend.mapper.ProjectMapper;
import com.example.backend.model.Project;
import com.example.backend.model.User;
import com.example.backend.repository.ProjectRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public Page<ProjectDTO> getByUser(String username, int size, int page) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username : " + username));

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        return projectRepository.findByUserId(user.getId(), pageable)
                .map(ProjectMapper::toDto);
    }

    public ProjectDTO addProject(ProjectDTO projectDTO) {
        // Get current authenticated user from SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new UsernameNotFoundException("User not authenticated");
        }
        
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with username : " + username));

        Project project = ProjectMapper.toModel(projectDTO);
        project.setUser(user);
        Project newProject = projectRepository.save(project);

        return ProjectMapper.toDto(newProject);
    }

    public void deleteById(Long id) {
        boolean projectExists = projectRepository.existsById(id);

        if (projectExists) {
            projectRepository.deleteById(id);
        } else  {
            throw new ProjectNotFoundException("Project with id : " + id + " not found");
        }
    }

    public ProjectDTO updateProject(ProjectDTO projectDTO) {
        Project project = projectRepository.findById(projectDTO.getId())
                .orElseThrow(() -> new ProjectNotFoundException(
                        "Project with id: " + projectDTO.getId() + " not found"));

        project.setTitle(projectDTO.getTitle());
        project.setDescription(projectDTO.getDescription());
        projectRepository.save(project);

        return ProjectMapper.toDto(project);
    }


}


















