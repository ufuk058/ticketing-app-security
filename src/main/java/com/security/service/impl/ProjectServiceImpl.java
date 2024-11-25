package com.security.service.impl;

import com.security.dto.ProjectDTO;
import com.security.dto.UserDTO;
import com.security.entity.Project;
import com.security.entity.User;
import com.security.enums.Status;
import com.security.mapper.MapperUtil;
import com.security.repository.ProjectRepository;
import com.security.service.ProjectService;
import com.security.service.TaskService;
import com.security.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final MapperUtil mapperUtil;
    private final UserService userService;
    private final TaskService taskService;

    public ProjectServiceImpl(ProjectRepository projectRepository, MapperUtil mapperUtil, UserService userService, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.mapperUtil = mapperUtil;

        this.userService = userService;
        this.taskService = taskService;
    }

    @Override
    public ProjectDTO findByProjectCode(String projectCode) {
        Project project =projectRepository.findByProjectCode(projectCode);

        return mapperUtil.convert(project,ProjectDTO.class);
    }

    @Override
    public List<ProjectDTO> listAllProjects() {
        List<Project> list= projectRepository.findAll(Sort.by("projectCode"));
        return list.stream().map(project ->mapperUtil.convert(project,ProjectDTO.class)).collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO project) {

        project.setProjectStatus(Status.OPEN);
        Project convertedProject= mapperUtil.convert(project,Project.class);
        projectRepository.save(convertedProject);
    }

    @Override
    public void update(ProjectDTO project) {
        Project foundProject=projectRepository.findByProjectCode(project.getProjectCode());
        Project updatedProject=mapperUtil.convert(project,Project.class);
        updatedProject.setId(foundProject.getId());
        updatedProject.setProjectStatus(foundProject.getProjectStatus());

        projectRepository.save(updatedProject);
    }

    @Override
    public void delete(String projectCode) {

        Project p1= projectRepository.findByProjectCode(projectCode);
        p1.setIsDeleted(true);
        p1.setProjectCode(p1.getProjectCode()+"-"+p1.getId());
        projectRepository.save(p1);
        taskService.deleteByProject(mapperUtil.convert(p1,ProjectDTO.class));


    }

    @Override
    public void complete(String projectCode) {
        Project project = projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);

        taskService.completeByProject(mapperUtil.convert(project,ProjectDTO.class));
    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() {
        UserDTO currentUser = userService.findByUserName("harold@manager.com");
        User user = mapperUtil.convert(currentUser,User.class);
        List<Project> list = projectRepository.findAllByAssignedManager(user);

       return list.stream().map(project -> {
            ProjectDTO dto= mapperUtil.convert(project,ProjectDTO.class);
            dto.setUnfinishedTaskCounts(taskService.totalNonCompletedTask(project.getProjectCode()));
            dto.setCompleteTaskCounts(taskService.totalCompletedTask(project.getProjectCode()));
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> listAllNonCompletedByAssignedManager(UserDTO assignedManager) {

        List<Project> projects =
                projectRepository.findAllByProjectStatusIsNotAndAssignedManager(Status.COMPLETE,mapperUtil.convert(assignedManager,User.class));
        return  projects.stream().map( project-> mapperUtil.convert(project,ProjectDTO.class)).collect(Collectors.toList());
    }
}
