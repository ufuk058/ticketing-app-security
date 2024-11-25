package com.security.converter;

import com.security.dto.RoleDTO;
import com.security.service.RoleService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleDtoConverter implements Converter<String, RoleDTO> {

    // "1" ==> RoleDTO

    private final RoleService roleService;

    public RoleDtoConverter(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public RoleDTO convert(String source) {  // "1" ==> 1

        if (source.isEmpty()){
            return null;
        }

        return roleService.findById(Long.parseLong(source));
    }
}
