package com.echarge.demo.repository;

import com.echarge.demo.entity.ConnectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectorRepository extends JpaRepository<ConnectorEntity, String> {
    Optional<ConnectorEntity> findOneByNumber(Integer number);

    Optional<ConnectorEntity> findOneByNumberAndChargePointId(Integer number, Long chargePointId);

    List<ConnectorEntity> findAllByChargePointId(Long chargePointId);
}
