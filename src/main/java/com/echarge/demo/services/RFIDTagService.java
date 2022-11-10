package com.echarge.demo.services;

import com.echarge.demo.entity.CustomerEntity;
import com.echarge.demo.entity.RFIDTagEntity;

import java.util.Collection;

public interface RFIDTagService {

    RFIDTagEntity findOneByNameAndNumber(String name, Integer number);

    Collection<RFIDTagEntity> findAllByCustomerId(Long customerId);

    RFIDTagEntity findOneByVehicleId(Long vehicleId);

    boolean belongsToCustomer(CustomerEntity customer, RFIDTagEntity rfidTag);
}
