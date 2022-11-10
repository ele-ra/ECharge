package com.echarge.demo.services;


import com.echarge.demo.entity.*;
import com.echarge.demo.payload.request.ChargingEventRequest;
import com.echarge.demo.payload.response.ChargingRequestResponse;
import com.echarge.demo.payload.response.MessageResponse;
import com.echarge.demo.repository.ChargePointRepository;
import com.echarge.demo.repository.ChargingSessionRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static java.lang.String.format;

@Service
public class ChargingEventProducerServiceImpl implements ChargingEventProducerService {


    private static final Logger log = LoggerFactory.getLogger(ChargingEventProducerServiceImpl.class);
    private final ChargingSessionRepository chargingSessionReporitory;
    private final CustomerService customerService;
    private final ConnectorService connectorService;
    private final VehicleService vehicleService;
    private final RFIDTagService rfidTagService;
    private final ChargePointService chargePointService;
    private final ChargingSessionService chargingSessionService;


    @Autowired
    public ChargingEventProducerServiceImpl(ChargePointRepository chargePointRepository, ChargingSessionRepository chargingSessionReporitory, CustomerService customerService, ConnectorService connectorService, VehicleService vehicleService, RFIDTagService rfidTagService, ChargePointService chargePointService, ChargingSessionService chargingSessionService) {
        this.chargingSessionReporitory = chargingSessionReporitory;
        this.customerService = customerService;
        this.connectorService = connectorService;
        this.vehicleService = vehicleService;
        this.rfidTagService = rfidTagService;
        this.chargePointService = chargePointService;
        this.chargingSessionService = chargingSessionService;
    }

    @Transactional
    @Override
    public ChargingRequestResponse startChargingEvent(Long userId, ChargingEventRequest request) throws IllegalAccessException {
        ChargingEventProperties chargingEventProp = new ChargingEventProperties(userId, request);
        ChargingSessionEntity session = startIfAbsent(chargingEventProp);

        ChargingRequestResponse res = new ChargingRequestResponse();
        res.setChargingSession(session);
        res.setRequest(request);

        res.setMessage(new MessageResponse(session == null ? "Error trying to start Charging Session" : "Charging session was started"));

        return res;
    }

    private ChargingSessionEntity startIfAbsent(ChargingEventProperties chargingEventProp) {
        if (chargingSessionService.isConnectorBusy(chargingEventProp.connector.getId()))
            throw new IllegalArgumentException(format("Provided Connector (number = %s) is busy " +
                    "please consider choosing another Charge Point / Connector " +
                    "or consider request admin to add more connectors", chargingEventProp.connector.getNumber()));

        VehicleEntity vehicle = vehicleService.findOneById(chargingEventProp.rfidTag.getVehicleId());

        ChargingSessionEntity chargingSession = chargingSessionService
                .findOneByCustomerIdAndConnectorIdAndVehicleIdAndEndTimeIsNull(chargingEventProp.customer.getId(),
                        chargingEventProp.connector.getId(), vehicle.getId());
        if (chargingSession != null) {
            throw new IllegalArgumentException(format("Previous Charging session wasn't ended " +
                            "for provided Charge Point (name = %s, Serial Number = %s), " +
                            "Connector (number = %s) and RFID Tag (name = %s, Number = %s)",
                    chargingEventProp.chargePoint.getName(), chargingEventProp.chargePoint.getSn(),
                    chargingEventProp.connector.getNumber(), chargingEventProp.rfidTag.getName(),
                    chargingEventProp.rfidTag.getNumber()));
        }

        return chargingSessionService.startSession(chargingEventProp.customer, chargingEventProp.connector, vehicle);
    }


    @Transactional
    @Override
    public ChargingRequestResponse endChargingEvent(Long userId, ChargingEventRequest request) throws IllegalAccessException {
        ChargingEventProperties chargingEventProp = new ChargingEventProperties(userId, request);

        ChargingSessionEntity session = endIfExists(chargingEventProp);

        ChargingRequestResponse res = new ChargingRequestResponse();
        res.setChargingSession(session);
        res.setRequest(request);

        res.setMessage(new MessageResponse(session == null ? "Error trying to end Charging Session" : "Charging session was ended"));

        return res;
    }

    private ChargingSessionEntity endIfExists(ChargingEventProperties chargingEventProp) {

        VehicleEntity vehicle = vehicleService.findOneById(chargingEventProp.rfidTag.getVehicleId());

        ChargingSessionEntity chargingSession = chargingSessionService.findOneByCustomerIdAndConnectorIdAndVehicleIdAndEndTimeIsNull(
                chargingEventProp.customer.getId(),
                chargingEventProp.connector.getId(), vehicle.getId());
        if (chargingSession == null) {
            throw new IllegalArgumentException(format("Ongoing Charging session not found " +
                            "for provided Charge Point (name = %s, Serial Number = %s), " +
                            "Connector (number = %s) and RFID Tag (name = %s, Number = %s)",
                    chargingEventProp.chargePoint.getName(), chargingEventProp.chargePoint.getSn(),
                    chargingEventProp.connector.getNumber(), chargingEventProp.rfidTag.getName(), chargingEventProp.rfidTag.getNumber()));
        }
        chargePointService.fetchErrorMessageIfPresent(chargingSessionService, chargingSession);
        ChargingSessionEntity session = chargingSessionService.endSession(chargingSession);
        vehicleService.updateMeter(chargingSession.getVehicleId(), chargingSession.getEndMeter());
        return session;
    }

    @Data
    private class ChargingEventProperties {
        private CustomerEntity customer;
        private ChargePointEntity chargePoint;
        private ConnectorEntity connector;
        private RFIDTagEntity rfidTag;

        public ChargingEventProperties(Long userId, ChargingEventRequest request) throws IllegalAccessException {
            customer = customerService.findOneByUserId(userId);
            chargePoint = chargePointService.findOneByNameAndSn(request.getChargePointName(), request.getChargePointSerialNumber());

            if (!chargePointService.belongsToCustomer(customer, chargePoint))
                throw new IllegalAccessException(format("Provided Charge Point (name = %s, Serial Number = %s) " +
                        "doesn't belong to requested customer", chargePoint.getName(), chargePoint.getSn()));

            connector = connectorService.findOneByNumber(request.getConnectorNumber());

            if (!connectorService.belongsToChargePoint(chargePoint, connector))
                throw new IllegalArgumentException(format("Provided Connector (number = %d) " +
                        "doesn't belong to requested Charge Point", connector.getNumber()));

            rfidTag = rfidTagService.findOneByNameAndNumber(request.getRdfidTagName(), request.getRdfidTagNumber());

            if (!rfidTagService.belongsToCustomer(customer, rfidTag))
                throw new IllegalAccessException(format("Provided RDFID Tag (name = %s, Number = %s) " +
                        "doesn't belong to requested customer", rfidTag.getName(), rfidTag.getNumber()));
        }
    }
}
