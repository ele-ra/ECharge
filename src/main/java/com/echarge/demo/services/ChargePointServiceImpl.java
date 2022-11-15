package com.echarge.demo.services;

import com.echarge.demo.entity.ChargePointEntity;
import com.echarge.demo.entity.ChargingSessionEntity;
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
    public ChargePointEntity findOneBySn(String sn) {
        return chargePointRepository.findOneBySnIgnoreCase(sn).orElseThrow((
                () -> new NoSuchElementException(format("ChargePoint with sn = %s, not found", sn))));
    }

    @Override
    public Collection<ChargePointEntity> findAllByCustomerId(long customerId) {
        return chargePointRepository.findAllByCustomerId(customerId);
    }

    @Override
    public boolean belongsToCustomer(long customer, long providedChargePointId) {
        return chargePointRepository.findAllByCustomerId(customer)
                .contains(chargePointRepository.findOneById(providedChargePointId));
    }

    @Override
    public void fetchErrorMessageIfPresent(ChargingSessionService chargingSessionService, long chargingSessionId) {
        ChargingSessionEntity session = chargingSessionService.findOneById(chargingSessionId);
        if (session.getStartTime().getTime() % 5 == 0)
            chargingSessionService.receiveMessageFromChargePoint("Error", session.getId());
    }
}
