package com.security.service;

import com.security.dto.ProjectDTO;
import com.security.dto.TaskDTO;
import com.security.dto.UserDTO;
import com.security.enums.Status;

import java.util.List;

public interface TaskService {

    TaskDTO findById(Long id);

    List<TaskDTO> listAllTasks();

    void save(TaskDTO task);
    void update(TaskDTO task);
    void delete(Long id);

    int totalNonCompletedTask(String projectCode);

    int totalCompletedTask(String projectCode);

    void deleteByProject(ProjectDTO project);

    void completeByProject(ProjectDTO project);

    List<TaskDTO> listAllTasksByStatusIsNot(Status complete);

    List<TaskDTO> litsAllTasksByStatus(Status status);

    List<TaskDTO> listAllNonCompletedByAssignedEmployee(UserDTO user);
}
