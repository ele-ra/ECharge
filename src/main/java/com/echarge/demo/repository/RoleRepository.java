package com.echarge.demo.repository;

import com.echarge.demo.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, String> {
    RoleEntity findByNameIgnoreCase(String name);

    RoleEntity findOneById(Long id);
}
