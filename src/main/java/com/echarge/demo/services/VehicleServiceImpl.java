package com.echarge.demo.services;

import com.echarge.demo.entity.VehicleEntity;
import com.echarge.demo.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

import static java.lang.String.format;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public VehicleEntity findOneByNameAndRegistrationPlate(String name, String registrationPlate) {
        return vehicleRepository.findOneByNameIgnoreCaseAndRegistrationPlateIgnoreCase(name, registrationPlate).orElseThrow((
                () -> new NoSuchElementException(format("Vehicle with name = %s and Registration Plate = %s not found", name, registrationPlate))));
    }

    @Override
    public VehicleEntity findOneById(long vehicleId) {
        return vehicleRepository.findOneById(vehicleId).orElseThrow((
                () -> new NoSuchElementException(format("Vehicle with id = %d not found", vehicleId))));
    }

    @Transactional
    @Override
    public void updateMeter(long vehicleId, double newMeter) {
        vehicleRepository.findOneById(vehicleId).orElseThrow((
                        () -> new NoSuchElementException(format("Vehicle with id = %d not found", vehicleId))))
                .setMeter(newMeter);
    }
}
