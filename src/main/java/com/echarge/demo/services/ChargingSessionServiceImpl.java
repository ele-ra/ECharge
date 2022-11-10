package com.echarge.demo.services;

import com.echarge.demo.entity.ChargingSessionEntity;
import com.echarge.demo.entity.ConnectorEntity;
import com.echarge.demo.entity.CustomerEntity;
import com.echarge.demo.entity.VehicleEntity;
import com.echarge.demo.repository.ChargingSessionRepository;
import com.echarge.demo.services.helper.ChargingSessionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

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
    public ChargingSessionEntity findOneByCustomerIdAndConnectorIdAndVehicleId(Long customerId, Long connectorId, Long vehicleId) {
        return chargingSessionRepository.findOneByCustomerIdAndConnectorIdAndVehicleId(customerId, connectorId, vehicleId)
                .orElseThrow((
                        () ->
                                new NoSuchElementException("Charging Event not found for given parameters")));
    }

    @Override
    public ChargingSessionEntity findOneByCustomerIdAndConnectorIdAndVehicleIdAndEndTimeIsNull(Long customerId, Long connectorId, Long vehicleId) {
        return chargingSessionRepository.findOneByCustomerIdAndConnectorIdAndVehicleIdAndEndTimeIsNull(customerId, connectorId, vehicleId);
    }

    @Override
    public List<ChargingSessionEntity> findAllByCustomerId(Long customerId) {
        return chargingSessionRepository.findAllByCustomerId(customerId);
    }

    @Override
    public List<ChargingSessionEntity> findAllByCustomerIdAndConnectorId(Long customerId, Long connectorId) {
        return chargingSessionRepository.findAllByCustomerIdAndConnectorId(customerId, connectorId);
    }

    @Override
    public List<ChargingSessionEntity> findAllByCustomerIdAndVehicleId(Long customerId, Long vehicleId) {
        return chargingSessionRepository.findAllByCustomerIdAndVehicleId(customerId, vehicleId);
    }


    @Override
    public boolean isConnectorBusy(Long connectorId) {
        return chargingSessionRepository.existsByConnectorIdAndEndTimeIsNull(connectorId);
    }

    @Override
    public ChargingSessionEntity startSession(CustomerEntity customer, ConnectorEntity connector, VehicleEntity vehicle) {
        ChargingSessionEntity session = new ChargingSessionEntity();

        session.setCustomerId(customer.getId());
        session.setConnectorId(connector.getId());
        session.setVehicleId(vehicle.getId());
        session.setStartMeter(vehicle.getMeter());
        session.setStartTime(Date.from(Instant.now()));

        chargingSessionRepository.save(session);
        return session;
    }

    @Override
    public ChargingSessionEntity endSession(ChargingSessionEntity session) {

        session.setEndTime(Date.from(Instant.now()));

        Date startTime = session.getStartTime();
        Date endTime = session.getEndTime();
        long chargingTime = calculateChargingTimeInMinutes(startTime, endTime);

        session.setEndMeter(calculateMeterValueIncrease(session.getStartMeter(), chargingTime));

        chargingSessionRepository.save(session);
        return session;
    }

    @Transactional
    public void receiveMessageFromChargePoint(String message, ChargingSessionEntity session) {
        session.setMessage(message);
    }

    private long calculateChargingTimeInMinutes(Date startTime, Date endTime) {
        long dif = endTime.getTime() - startTime.getTime();
        return (dif / 1000) % 60;
    }

    private Double calculateMeterValueIncrease(Double startMeter, long chargingTime) {
        return (startMeter + 0.1) * chargingTime * CHARGE_COEFFICIENT;
    }

    @Override
    public Collection<ChargingSessionEntity> findAllByTimeBetween(ChargingSessionFilter filter) {
        return chargingSessionRepository.findAllByStartTimeGreaterThanAndEndTimeLessThan(filter.getDateFrom(), filter.getDateTo());
    }

}
