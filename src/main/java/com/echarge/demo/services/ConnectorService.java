package com.echarge.demo.services;

import com.echarge.demo.entity.ChargePointEntity;
import com.echarge.demo.entity.ConnectorEntity;

import java.util.Collection;

public interface ConnectorService {
    ConnectorEntity findOneByNumber(Integer number);

    ConnectorEntity findOneByNumberAndChargePointId(Integer name, Long chargePointId);

    Collection<ConnectorEntity> findAllByChargePointId(Long chargePointId);

    boolean belongsToChargePoint(ChargePointEntity chargePoint, ConnectorEntity providedConnector);

    ConnectorEntity createIfAbsent(Integer number, ChargePointEntity chargePoint);
}
