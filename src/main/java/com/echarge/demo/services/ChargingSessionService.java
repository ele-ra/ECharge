package com.echarge.demo.services;

import com.echarge.demo.entity.ChargingSessionEntity;
import com.echarge.demo.entity.VehicleEntity;
import com.echarge.demo.services.helper.ChargingSessionFilter;

import java.util.Collection;


public interface ChargingSessionService {
    ChargingSessionEntity findOneByCustomerIdAndConnectorIdAndVehicleId(long customerId, long connectorId, long vehicleId);

    ChargingSessionEntity findOneByCustomerIdAndConnectorIdAndVehicleIdAndEndTimeIsNull(long customerId, long connectorId, long vehicleId);

    Collection<ChargingSessionEntity> findAllByCustomerId(long customerId);

    Collection<ChargingSessionEntity> findAllByCustomerIdAndConnectorId(long customerId, long connectorId);

    Collection<ChargingSessionEntity> findAllByCustomerIdAndVehicleId(long customerId, long vehicleId);

    boolean isConnectorBusy(long connectorId);

    ChargingSessionEntity startSession(long customerId, long connectorId, VehicleEntity vehicleId);

    ChargingSessionEntity endSession(long sessionId);

    void receiveMessageFromChargePoint(String message, long sessionId);

    Collection<ChargingSessionEntity> findAllByTimeBetween(ChargingSessionFilter filter);

    ChargingSessionEntity findOneById(long chargingSessionId);
}
