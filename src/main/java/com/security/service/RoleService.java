package com.security.service;

import com.security.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    List<RoleDTO> listAllRoles();

    RoleDTO findById(Long Id);
}
