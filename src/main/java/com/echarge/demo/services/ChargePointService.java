package com.echarge.demo.services;

import com.echarge.demo.entity.ChargePointEntity;
import com.echarge.demo.entity.ChargingSessionEntity;
import com.echarge.demo.entity.CustomerEntity;

import java.util.Collection;

public interface ChargePointService {
    ChargePointEntity findOneByNameAndSn(String name, String sn);

    Collection<ChargePointEntity> findAllByCustomerId(Long customerId);

    boolean belongsToCustomer(CustomerEntity customer, ChargePointEntity providedChargePoint);

    void fetchErrorMessageIfPresent(ChargingSessionService chargingSessionService, ChargingSessionEntity chargingSession);
}
