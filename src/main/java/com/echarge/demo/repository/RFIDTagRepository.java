package com.echarge.demo.repository;

import com.echarge.demo.entity.RFIDTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RFIDTagRepository extends JpaRepository<RFIDTagEntity, Long> {

    Optional<RFIDTagEntity> findOneByNameAndNumber(String name, int number);

    List<RFIDTagEntity> findAllByCustomerId(long customerId);

    Optional<RFIDTagEntity> findOneByVehicleId(long vehicleId);

    RFIDTagEntity findOneById(long rfidTagId);

    Optional<RFIDTagEntity> findOneByNumber(int number);
}
