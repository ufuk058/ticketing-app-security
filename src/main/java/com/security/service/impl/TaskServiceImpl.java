package com.security.service.impl;

import com.security.dto.ProjectDTO;
import com.security.dto.TaskDTO;
import com.security.dto.UserDTO;
import com.security.entity.Project;
import com.security.entity.Task;
import com.security.entity.User;
import com.security.enums.Status;
import com.security.mapper.MapperUtil;
import com.security.repository.TaskRepository;
import com.security.service.TaskService;
import com.security.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final MapperUtil mapperUtil;
    private final UserService userService;

    public TaskServiceImpl(TaskRepository taskRepository, MapperUtil mapperUtil, UserService userService) {
        this.taskRepository = taskRepository;
        this.mapperUtil = mapperUtil;
        this.userService = userService;
    }

    @Override
    public TaskDTO findById(Long id) {

        Optional<Task> task = taskRepository.findById(id);

        if(task.isPresent()){
            return mapperUtil.convert(task,TaskDTO.class);
        }
        return null;
    }

    @Override
    public List<TaskDTO> listAllTasks() {
        return taskRepository.findAll().stream().map(task->mapperUtil.convert(task,TaskDTO.class)).collect(Collectors.toList());
    }

    @Override
    public void save(TaskDTO task) {
        task.setAssignedDate(LocalDate.now());
        task.setTaskStatus(Status.OPEN);
        Task convertedTask=mapperUtil.convert(task,Task.class);
        taskRepository.save(convertedTask);

    }

    @Override
    public void update(TaskDTO dto) {

       Optional<Task> foundTask=taskRepository.findById(dto.getId());
       Task convertedTask =mapperUtil.convert(dto,Task.class);

       if(foundTask.isPresent()){
           convertedTask.setAssignedDate(foundTask.get().getAssignedDate());
           convertedTask.setTaskStatus(dto.getTaskStatus() == null ? foundTask.get().getTaskStatus() : dto.getTaskStatus() );
           taskRepository.save(convertedTask);
       }

    }

    @Override
    public void delete(Long id) {
        Optional<Task> foundTask= taskRepository.findById(id);

        if(foundTask.isPresent()){
            foundTask.get().setIsDeleted(true);
            taskRepository.save(foundTask.get());
        }

    }

    @Override
    public int totalNonCompletedTask(String projectCode) {

        return taskRepository.totalNonCompletedTask(projectCode);
    }

    @Override
    public int totalCompletedTask(String projectCode) {
        return taskRepository.totalCompletedTask(projectCode);
    }

    @Override
    public void deleteByProject(ProjectDTO project) {
      List<Task> taskToDelete =  taskRepository.findAllByProject(mapperUtil.convert(project, Project.class));
      taskToDelete.forEach(task -> delete(task.getId()));
    }

    @Override
    public void completeByProject(ProjectDTO project) {
        List<Task> taskToComplete =  taskRepository.findAllByProject(mapperUtil.convert(project, Project.class));
        taskToComplete.forEach(task ->{
            TaskDTO taskDTO= mapperUtil.convert(task,TaskDTO.class);
            taskDTO.setTaskStatus(Status.COMPLETE);
            update(taskDTO);
        });

    }

    @Override
    public List<TaskDTO> listAllTasksByStatusIsNot(Status status) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO loggedInUser= userService.findByUserName(username);
        List<Task> tasks = taskRepository.findAllByTaskStatusIsNotAndAssignedEmployee(status, mapperUtil.convert(loggedInUser, User.class));
        return tasks.stream().map(task -> mapperUtil.convert(task,TaskDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> litsAllTasksByStatus(Status status) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO loggedInUser= userService.findByUserName(username);
        List<Task> tasks = taskRepository.findAllByTaskStatusAndAssignedEmployee(status, mapperUtil.convert(loggedInUser, User.class));
        return tasks.stream().map(task -> mapperUtil.convert(task,TaskDTO.class)).collect(Collectors.toList());


    }

    @Override
    public List<TaskDTO> listAllNonCompletedByAssignedEmployee(UserDTO employee) {
       List<Task> tasks = taskRepository.findAllByTaskStatusIsNotAndAssignedEmployee(Status.COMPLETE,mapperUtil.convert(employee,User.class));

        return tasks.stream().map( task -> mapperUtil.convert(task, TaskDTO.class)).collect(Collectors.toList());
    }
}
