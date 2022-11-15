package com.echarge.demo.services;

import com.echarge.demo.entity.RFIDTagEntity;

import java.util.Collection;

public interface RFIDTagService {

    RFIDTagEntity findOneByNumber(int number);

    Collection<RFIDTagEntity> findAllByCustomerId(long customerId);

    RFIDTagEntity findOneByVehicleId(long vehicleId);

    boolean belongsToCustomer(long customerId, long rfidTagId);
}
