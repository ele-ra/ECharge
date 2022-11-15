package com.echarge.demo.repository;

import com.echarge.demo.entity.ChargingSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChargingSessionRepository extends JpaRepository<ChargingSessionEntity, Long> {
    Optional<ChargingSessionEntity> findOneByCustomerIdAndConnectorIdAndVehicleId(long customerId, long connectorId, long vehicleId);

    List<ChargingSessionEntity> findAllByCustomerId(long customerId);

    List<ChargingSessionEntity> findAllByCustomerIdAndConnectorId(long customerId, long connectorId);

    List<ChargingSessionEntity> findAllByCustomerIdAndVehicleId(long customerId, long vehicleId);

    boolean existsByConnectorIdAndEndTimeIsNull(long connectorId);

    ChargingSessionEntity findOneByCustomerIdAndConnectorIdAndVehicleIdAndEndTimeIsNull(long customerId, long connectorId, long vehicleId);

    List<ChargingSessionEntity> findAllByStartTimeGreaterThanEqualAndEndTimeLessThanEqual(Date dateFrom, Date dateTo);

    ChargingSessionEntity findOneById(long sessionId);

    List<ChargingSessionEntity> findAllByStartTimeGreaterThanEqual(Date dateFrom);
}
