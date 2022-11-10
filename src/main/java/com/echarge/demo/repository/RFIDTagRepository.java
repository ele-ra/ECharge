package com.echarge.demo.repository;

import com.echarge.demo.entity.RFIDTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RFIDTagRepository extends JpaRepository<RFIDTagEntity, String> {

    Optional<RFIDTagEntity> findOneByNameAndNumber(String name, Integer number);

    List<RFIDTagEntity> findAllByCustomerId(Long customerId);

    Optional<RFIDTagEntity> findOneByVehicleId(Long vehicleId);
}
