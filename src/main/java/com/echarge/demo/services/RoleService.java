package com.echarge.demo.services;

import com.echarge.demo.entity.RoleEntity;

import java.util.List;

public interface RoleService {
    List<RoleEntity> findAll();

    RoleEntity findOneByName(String name);

    RoleEntity findOneById(Long id);
}
