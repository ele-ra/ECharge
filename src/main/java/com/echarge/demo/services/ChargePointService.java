package com.echarge.demo.services;

import com.echarge.demo.entity.ChargePointEntity;

import java.util.Collection;

public interface ChargePointService {
    ChargePointEntity findOneBySn(String sn);

    Collection<ChargePointEntity> findAllByCustomerId(long customerId);

    boolean belongsToCustomer(long customer, long providedChargePointId);

    void fetchErrorMessageIfPresent(ChargingSessionService chargingSessionService, long sessionId);
}
