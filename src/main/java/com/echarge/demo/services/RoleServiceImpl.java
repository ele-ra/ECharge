package com.echarge.demo.services;

import com.echarge.demo.entity.RoleEntity;
import com.echarge.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Cacheable(value = "roles")
    public List<RoleEntity> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public RoleEntity findOneByName(String name) {
        return roleRepository.findByNameIgnoreCase(name);
    }

    @Override
    @Cacheable(value = "rolesById")
    public RoleEntity findOneById(long id) {
        return roleRepository.findOneById(id);
    }
}
