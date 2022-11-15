package com.echarge.demo.services;

import com.echarge.demo.entity.ChargePointEntity;
import com.echarge.demo.entity.ConnectorEntity;

import java.util.Collection;

public interface ConnectorService {
    ConnectorEntity findOneByNumber(int number);

    ConnectorEntity findOneByNumberAndChargePointId(int number, long chargePointId);

    Collection<ConnectorEntity> findAllByChargePointId(long chargePointId);

    boolean belongsToChargePoint(long chargePointId, long providedConnectorId);

    ConnectorEntity createIfAbsent(int number, ChargePointEntity chargePoint);
}
