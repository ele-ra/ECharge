package com.echarge.demo.repository;

import com.echarge.demo.entity.ChargingSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChargingSessionRepository extends JpaRepository<ChargingSessionEntity, String> {
    Optional<ChargingSessionEntity> findOneByCustomerIdAndConnectorIdAndVehicleId(Long customerId, Long connectorId, Long vehicleId);

    List<ChargingSessionEntity> findAllByCustomerId(Long customerId);

    List<ChargingSessionEntity> findAllByCustomerIdAndConnectorId(Long customerId, Long connectorId);

    List<ChargingSessionEntity> findAllByCustomerIdAndVehicleId(Long customerId, Long vehicleId);

    List<ChargingSessionEntity> findAllByConnectorIdAndEndTimeIsNull(Long connectorId);

    boolean existsByConnectorIdAndEndTimeIsNull(Long connectorId);

    ChargingSessionEntity findOneByCustomerIdAndConnectorIdAndVehicleIdAndEndTimeIsNull(Long customerId, Long connectorId, Long vehicleId);

    List<ChargingSessionEntity> findAllByStartTimeGreaterThanAndEndTimeLessThan(Date dateFrom, Date dateTo);

    List<ChargingSessionEntity> findAllByConnectorId(Long connectorId);
}
