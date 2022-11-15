package com.echarge.demo.services;

import com.echarge.demo.entity.VehicleEntity;

public interface VehicleService {
    VehicleEntity findOneByNameAndRegistrationPlate(String name, String registrationPlate);

    VehicleEntity findOneById(long vehicleId);

    void updateMeter(long vehicleId, double newMeter);
}

