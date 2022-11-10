package com.echarge.demo.services;

import com.echarge.demo.entity.VehicleEntity;

public interface VehicleService {
    VehicleEntity findOneByNameAndRegistrationPlate(String name, String registrationPlate);

    VehicleEntity findOneById(Long vehicleId);

    void updateMeter(Long vehicleId, Double newMeter);
}

