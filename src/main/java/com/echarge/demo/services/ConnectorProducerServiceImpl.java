package com.echarge.demo.services;

import com.echarge.demo.entity.ChargePointEntity;
import com.echarge.demo.entity.ConnectorEntity;
import com.echarge.demo.payload.request.ConnectorAddRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ConnectorProducerServiceImpl implements ConnectorProducerService {
    private static final Logger log = LoggerFactory.getLogger(ChargingEventProducerServiceImpl.class);
    private final ConnectorService connectorService;
    private final ChargePointService ChargePointService;

    @Autowired
    public ConnectorProducerServiceImpl(ConnectorService connectorService, ChargePointService ChargePointService) {
        this.connectorService = connectorService;
        this.ChargePointService = ChargePointService;
    }

    @Transactional
    @Override
    public ConnectorEntity addConnector(ConnectorAddRequest request) {
        ChargePointEntity chargePoint = ChargePointService.findOneBySn(request.getChargePointSerialNumber());
        return connectorService.createIfAbsent(request.getConnectorNumber(), chargePoint);
    }
}
