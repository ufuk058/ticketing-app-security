package com.security.service.impl;

import com.security.entity.User;
import com.security.entity.UserPrincipal;
import com.security.repository.UserRepository;
import com.security.service.SecurityService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {
    private final UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepository.findByUserNameAndIsDeleted(username,false);

        return new UserPrincipal(user);
    }
}
