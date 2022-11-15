package com.echarge.demo.services;

import com.echarge.demo.entity.UserEntity;
import com.echarge.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) {
        return fundUserByName(name);
    }

    @Override
    public UserEntity fundUserByName(String name) {
        return userRepository.findOneByUsernameIgnoreCase(name).orElseThrow((
                () -> new UsernameNotFoundException(format("User with username - %s, not found", name))));
    }

    @Override
    public UserEntity findUserById(long id) {
        return userRepository.findById(id).orElseThrow((
                () -> new UsernameNotFoundException(format("User with id - %d, not found", id))));
    }
}