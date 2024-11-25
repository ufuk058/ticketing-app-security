package com.security.service.impl;

import com.security.dto.RoleDTO;
import com.security.entity.Role;
import com.security.mapper.MapperUtil;
import com.security.mapper.RoleMapper;
import com.security.repository.RoleRepository;
import com.security.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
   private final RoleRepository roleRepository;
   //private final RoleMapper roleMapper;
    private final MapperUtil mapperUtil;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper, MapperUtil mapperUtil) {
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
       // this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDTO> listAllRoles() {
        //ask repository layer to give us a list of roles from database
        List<Role> roleList=roleRepository.findAll();
        // if provide roleList to return type it will give error because roleList is Role object but we need to
        // return RoleDTO so we need a mechanism to convert Role to RoleDTO which is RoleMapper
       // return roleList.stream().map(roleMapper::convertToDto).collect(Collectors.toList());

        return roleList.stream().map(role ->mapperUtil.convert(role,RoleDTO.class)).collect(Collectors.toList());
    }

    @Override
    public RoleDTO findById(Long id) {

        Role role=roleRepository.findById(id).get();
        return mapperUtil.convert(role,RoleDTO.class);
    }
}
