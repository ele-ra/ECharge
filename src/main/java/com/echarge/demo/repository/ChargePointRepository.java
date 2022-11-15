package com.echarge.demo.repository;

import com.echarge.demo.entity.ChargePointEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChargePointRepository extends JpaRepository<ChargePointEntity, Long> {
    List<ChargePointEntity> findAllByCustomerId(long customerId);

    Optional<ChargePointEntity> findOneBySnIgnoreCase(String sn);

    ChargePointEntity findOneById(long providedChargePointId);
}
