package com.echarge.demo.repository;

import com.echarge.demo.entity.ConnectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectorRepository extends JpaRepository<ConnectorEntity, Long> {
    Optional<ConnectorEntity> findOneByNumber(int number);

    Optional<ConnectorEntity> findOneByNumberAndChargePointId(int number, long chargePointId);

    List<ConnectorEntity> findAllByChargePointId(long chargePointId);

    ConnectorEntity findOneById(long providedConnectorId);
}
