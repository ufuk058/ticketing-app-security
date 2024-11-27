package com.security.service.impl;

import com.security.dto.ProjectDTO;
import com.security.dto.TaskDTO;
import com.security.dto.UserDTO;
import com.security.entity.User;
import com.security.mapper.MapperUtil;
import com.security.repository.UserRepository;
import com.security.service.ProjectService;
import com.security.service.TaskService;
import com.security.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil, @Lazy ProjectService projectService, @Lazy TaskService taskService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
       // this.userMapper = userMapper;
        this.mapperUtil = mapperUtil;
        this.projectService = projectService;
        this.taskService = taskService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> listAllUsers() {
        List<User> userList=userRepository.findAllByIsDeletedOrderByFirstNameDesc(false);

       // return userList.stream().map(userMapper::convertToDto).collect(Collectors.toList());
        return userList.stream().map(user->mapperUtil.convert(user, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {
        User user= userRepository.findByUserNameAndIsDeleted(username,false);
        return mapperUtil.convert(user,UserDTO.class);
    }

    @Override
    public void save(UserDTO dto) {

        User convertedUser= mapperUtil.convert(dto,User.class);
        convertedUser.setPassWord(passwordEncoder.encode(convertedUser.getPassWord()));
        userRepository.save(convertedUser);

    }

    @Override
    public void update(UserDTO dto) {

        User foundUser=userRepository.findByUserNameAndIsDeleted(dto.getUserName(),false);
        User updatedUser=mapperUtil.convert(dto,User.class);
        updatedUser.setId(foundUser.getId());
        userRepository.save(updatedUser);
    }

    @Override
    public void delete(String username) {

        User user= userRepository.findByUserNameAndIsDeleted(username,false);

        if(checkIfUserCanBeDeleted(mapperUtil.convert(user,UserDTO.class))){
            user.setIsDeleted(true);
            user.setUserName(user.getUserName()+"-"+user.getId());
            userRepository.save(user);
        }


    }
    private boolean checkIfUserCanBeDeleted(UserDTO user){
        switch(user.getRole().getDescription()){
            case "Manager":
                List<ProjectDTO> projectDTOList= projectService.listAllNonCompletedByAssignedManager(user);
                return  projectDTOList.size()==0;

            case "Employee":
                List<TaskDTO> taskDTOList= taskService.listAllNonCompletedByAssignedEmployee(user);

                return  taskDTOList.size()==0;

            default:
                return true;
        }
    }

    @Override
    public List<UserDTO> listAllByRole(String role){
        List<User> users=userRepository.findByRoleDescriptionIgnoreCaseAndIsDeleted(role,false);
        return users.stream().map(user -> mapperUtil.convert(user,UserDTO.class)).collect(Collectors.toList());
    }
}

