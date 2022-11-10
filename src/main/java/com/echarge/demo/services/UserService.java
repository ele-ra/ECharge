package com.echarge.demo.services;

import com.echarge.demo.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    UserDetails loadUserByUsername(String username);

    UserEntity fundUserByName(String username);

    UserEntity findUserById(Long id);
}
