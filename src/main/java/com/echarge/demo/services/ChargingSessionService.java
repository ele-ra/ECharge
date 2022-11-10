package com.echarge.demo.services;

import com.echarge.demo.entity.ChargingSessionEntity;
import com.echarge.demo.entity.ConnectorEntity;
import com.echarge.demo.entity.CustomerEntity;
import com.echarge.demo.entity.VehicleEntity;
import com.echarge.demo.services.helper.ChargingSessionFilter;

import java.util.Collection;


public interface ChargingSessionService {
    ChargingSessionEntity findOneByCustomerIdAndConnectorIdAndVehicleId(Long customerId, Long connectorId, Long vehicleId);

    ChargingSessionEntity findOneByCustomerIdAndConnectorIdAndVehicleIdAndEndTimeIsNull(Long customerId, Long connectorId, Long vehicleId);

    Collection<ChargingSessionEntity> findAllByCustomerId(Long customerId);

    Collection<ChargingSessionEntity> findAllByCustomerIdAndConnectorId(Long customerId, Long connectorId);

    Collection<ChargingSessionEntity> findAllByCustomerIdAndVehicleId(Long customerId, Long vehicleId);

    boolean isConnectorBusy(Long connectorId);

    ChargingSessionEntity startSession(CustomerEntity customer, ConnectorEntity connector, VehicleEntity vehicle);

    ChargingSessionEntity endSession(ChargingSessionEntity session);

    void receiveMessageFromChargePoint(String message, ChargingSessionEntity session);

    Collection<ChargingSessionEntity> findAllByTimeBetween(ChargingSessionFilter filter);
}
