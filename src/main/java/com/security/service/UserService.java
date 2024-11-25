package com.security.service;

import com.security.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> listAllUsers();
    UserDTO findByUserName(String username);
    void save(UserDTO user);
    void update(UserDTO user);
    void delete(String username);

    List<UserDTO> listAllByRole(String role);
}
