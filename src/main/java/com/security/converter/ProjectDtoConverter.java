package com.security.converter;

import com.security.dto.ProjectDTO;
import com.security.service.ProjectService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProjectDtoConverter implements Converter<String, ProjectDTO> { // PR001 -> ProjectDTO

    private final ProjectService projectService;

    public ProjectDtoConverter(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public ProjectDTO convert(String source) {
        return projectService.findByProjectCode(source);
    }
}