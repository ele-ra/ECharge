package com.echarge.demo.services;

import com.echarge.demo.entity.ChargePointEntity;
import com.echarge.demo.entity.ChargingSessionEntity;
import com.echarge.demo.entity.CustomerEntity;
import com.echarge.demo.repository.ChargePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;

import static java.lang.String.format;

@Service
public class ChargePointServiceImpl implements ChargePointService {

    private final ChargePointRepository chargePointRepository;

    @Autowired
    public ChargePointServiceImpl(ChargePointRepository chargePointRepository) {
        this.chargePointRepository = chargePointRepository;
    }

    @Override
    public ChargePointEntity findOneByNameAndSn(String name, String sn) {
        return chargePointRepository.findOneByNameAndSn(name, sn).orElseThrow((
                () -> new NoSuchElementException(format("ChargePoint with name = %s and sn = %s, not found", name, sn))));
    }

    @Override
    public Collection<ChargePointEntity> findAllByCustomerId(Long customerId) {
        return chargePointRepository.findAllByCustomerId(customerId);
    }

    @Override
    public boolean belongsToCustomer(CustomerEntity customer, ChargePointEntity providedChargePoint) {
        return chargePointRepository.findAllByCustomerId(customer.getId()).contains(providedChargePoint);
    }

    @Override
    public void fetchErrorMessageIfPresent(ChargingSessionService chargingSessionService, ChargingSessionEntity chargingSession) {
        if (chargingSession.getStartTime().getTime() % 5 == 0)
            ;
        //chargingSessionService.receiveMessageFromChargePoint("Error", chargingSession);
    }
}
