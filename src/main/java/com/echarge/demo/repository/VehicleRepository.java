package com.echarge.demo.repository;

import com.echarge.demo.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    Optional<VehicleEntity> findOneByNameIgnoreCaseAndRegistrationPlateIgnoreCase(String name, String registrationPlate);

    Optional<VehicleEntity> findOneById(long vehicleId);
}

