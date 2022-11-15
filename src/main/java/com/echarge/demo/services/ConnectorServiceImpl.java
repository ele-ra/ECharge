package com.echarge.demo.services;

import com.echarge.demo.entity.ChargePointEntity;
import com.echarge.demo.entity.ConnectorEntity;
import com.echarge.demo.repository.ConnectorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;

import static java.lang.String.format;

@Service
public class ConnectorServiceImpl implements ConnectorService {
    private static final Logger log = LoggerFactory.getLogger(ConnectorServiceImpl.class);

    private final ConnectorRepository connectorRepository;

    @Autowired
    public ConnectorServiceImpl(ConnectorRepository connectorRepository) {
        this.connectorRepository = connectorRepository;
    }

    @Override
    public ConnectorEntity findOneByNumber(int number) {
        return connectorRepository.findOneByNumber(number).orElseThrow((
                () -> new NoSuchElementException(format("Connector with number = %s not found", number))));
    }

    @Override
    public ConnectorEntity findOneByNumberAndChargePointId(int number, long chargePointId) {
        return connectorRepository.findOneByNumberAndChargePointId(number, chargePointId).orElseThrow((
                () -> new NoSuchElementException(format("Connector with number = %s not found for a given chargePointId", number))));
    }

    @Override
    public Collection<ConnectorEntity> findAllByChargePointId(long chargePointId) {
        return connectorRepository.findAllByChargePointId(chargePointId);
    }

    @Override
    public boolean belongsToChargePoint(long chargePointId, long providedConnectorId) {
        return connectorRepository.findAllByChargePointId(chargePointId)
                .contains(connectorRepository.findOneById(providedConnectorId));
    }

    @Override
    public ConnectorEntity createIfAbsent(int number, ChargePointEntity chargePoint) {
        connectorRepository.findOneByNumberAndChargePointId(number, chargePoint.getId()).ifPresent(
                (x) -> {
                    throw new NoSuchElementException(format("Connector with number = %s and Charge Point (name = %s, Serial Number = %s) already exists",
                            number, chargePoint.getName(), chargePoint.getSn()));
                });

        ConnectorEntity connector = new ConnectorEntity();
        connector.setNumber(number);
        connector.setChargePointId(chargePoint.getId());

        log.info("CONNECTOR_SERVICE: Add new connector id={}, number={}, chargePointId={}", connector.getId(), connector.getNumber(), connector.getChargePointId());
        connectorRepository.save(connector);

        return connector;
    }
}
