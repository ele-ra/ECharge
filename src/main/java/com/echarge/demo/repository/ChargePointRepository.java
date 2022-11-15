package com.echarge.demo.repository;

import com.echarge.demo.entity.ChargePointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChargePointRepository extends JpaRepository<ChargePointEntity, Long> {

    Optional<ChargePointEntity> findOneByNameAndSn(String name, String sn);

    List<ChargePointEntity> findAllByCustomerId(long customerId);

    Optional<ChargePointEntity> findOneBySnIgnoreCase(String sn);

    ChargePointEntity findOneById(long providedChargePointId);
}
