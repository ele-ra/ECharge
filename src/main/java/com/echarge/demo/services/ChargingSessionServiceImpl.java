package com.echarge.demo.services;

import com.echarge.demo.entity.ChargingSessionEntity;
import com.echarge.demo.entity.VehicleEntity;
import com.echarge.demo.repository.ChargingSessionRepository;
import com.echarge.demo.services.helper.ChargingSessionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static java.time.ZoneOffset.UTC;

@Service
public class ChargingSessionServiceImpl implements ChargingSessionService {
    private static final Logger log = LoggerFactory.getLogger(ChargingSessionServiceImpl.class);

    private static final double CHARGE_COEFFICIENT = 1.01;
    private final ChargingSessionRepository chargingSessionRepository;

    @Autowired
    public ChargingSessionServiceImpl(ChargingSessionRepository chargingSessionRepository) {
        this.chargingSessionRepository = chargingSessionRepository;
    }

    @Override
    public ChargingSessionEntity findOneByCustomerIdAndConnectorIdAndVehicleId(long customerId, long connectorId, long vehicleId) {
        return chargingSessionRepository.findOneByCustomerIdAndConnectorIdAndVehicleId(customerId, connectorId, vehicleId)
                .orElseThrow((
                        () ->
                                new NoSuchElementException("Charging Event not found for given parameters")));
    }

    @Override
    public ChargingSessionEntity findOneByCustomerIdAndConnectorIdAndVehicleIdAndEndTimeIsNull(long customerId, long connectorId, long vehicleId) {
        return chargingSessionRepository.findOneByCustomerIdAndConnectorIdAndVehicleIdAndEndTimeIsNull(customerId, connectorId, vehicleId);
    }

    @Cacheable(value = "chargingSessionByCustomerId")
    @Override
    public List<ChargingSessionEntity> findAllByCustomerId(long customerId) {
        return chargingSessionRepository.findAllByCustomerId(customerId);
    }

    @Override
    public List<ChargingSessionEntity> findAllByCustomerIdAndConnectorId(long customerId, long connectorId) {
        return chargingSessionRepository.findAllByCustomerIdAndConnectorId(customerId, connectorId);
    }

    @Override
    public List<ChargingSessionEntity> findAllByCustomerIdAndVehicleId(long customerId, long vehicleId) {
        return chargingSessionRepository.findAllByCustomerIdAndVehicleId(customerId, vehicleId);
    }


    @Override
    public boolean isConnectorBusy(long connectorId) {
        return chargingSessionRepository.existsByConnectorIdAndEndTimeIsNull(connectorId);
    }

    @CacheEvict(value = { "chargingSessionByCustomerId"}, allEntries = true)
    @Override
    public ChargingSessionEntity startSession(long customerId, long connectorId, VehicleEntity vehicle) {
        ChargingSessionEntity session = new ChargingSessionEntity();

        session.setCustomerId(customerId);
        session.setConnectorId(connectorId);
        session.setVehicleId(vehicle.getId());
        session.setStartMeter(vehicle.getMeter());
        session.setStartTime(Date.from(Instant.now().atZone(UTC).toInstant()));

        chargingSessionRepository.save(session);
        return session;
    }

    @Override
    public ChargingSessionEntity endSession(long sessionId) {

        ChargingSessionEntity session = chargingSessionRepository.findOneById(sessionId);
        session.setEndTime(Date.from(Instant.now().atZone(UTC).toInstant()));
        Date startTime = session.getStartTime();
        Date endTime = session.getEndTime();
        long chargingTime = calculateChargingTimeInMinutes(startTime, endTime);

        session.setEndMeter(calculateMeterValueIncrease(session.getStartMeter(), chargingTime));

        chargingSessionRepository.save(session);
        return session;
    }

    @Transactional
    public void receiveMessageFromChargePoint(String message, long sessionId) {
        chargingSessionRepository.findOneById(sessionId).setMessage(message);
    }

    private long calculateChargingTimeInMinutes(Date startTime, Date endTime) {
        long dif = endTime.getTime() - startTime.getTime();
        return (dif / 1000) % 60;
    }

    private Double calculateMeterValueIncrease(double startMeter, long chargingTime) {
        double newMeter = startMeter - chargingTime * CHARGE_COEFFICIENT;
        return newMeter >= 0 ? newMeter : 0;
    }

    @Override
    public Collection<ChargingSessionEntity> findAllByTimeBetween(ChargingSessionFilter filter) {
        if (filter.getDateTo() == null)
            return chargingSessionRepository.findAllByStartTimeGreaterThanEqual(filter.getDateFrom());
        else
            return chargingSessionRepository.findAllByStartTimeGreaterThanEqualAndEndTimeLessThanEqual(filter.getDateFrom(), filter.getDateTo());
    }

    @Override
    public ChargingSessionEntity findOneById(long sessionId) {
        return chargingSessionRepository.findOneById(sessionId);
    }

}
