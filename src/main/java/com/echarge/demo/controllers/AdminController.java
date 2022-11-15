package com.echarge.demo.controllers;

import com.echarge.demo.entity.ChargingSessionEntity;
import com.echarge.demo.entity.ConnectorEntity;
import com.echarge.demo.payload.request.ConnectorAddRequest;
import com.echarge.demo.services.ChargePointService;
import com.echarge.demo.services.ChargingEventProducerService;
import com.echarge.demo.services.ChargingSessionService;
import com.echarge.demo.services.ConnectorProducerService;
import com.echarge.demo.services.helper.ChargingSessionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AuthenticationManager authenticationManager;

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    private final ChargingEventProducerService chargingEventProducerService;
    private final ChargePointService ChargePointService;
    private final ChargingSessionService chargingSessionService;
    private final ConnectorProducerService connectorProducerService;

    @Autowired
    public AdminController(ChargingEventProducerService chargingEventProducerService, ChargePointService ChargePointService, ChargingSessionService chargingSessionService, ConnectorProducerService connectorProducerService) {
        this.chargingEventProducerService = chargingEventProducerService;
        this.ChargePointService = ChargePointService;
        this.chargingSessionService = chargingSessionService;
        this.connectorProducerService = connectorProducerService;
    }

    @Transactional
    @PostMapping(
            value = "/connector/create",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConnectorEntity> createConnector(@Valid @RequestBody ConnectorAddRequest connectorAddrequest, Authentication auth) {

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        log.info("ADMIN_CONTROLLER: Adding connector requested by user = {}", userDetails.getUsername());
        return new ResponseEntity<>(connectorProducerService.addConnector(connectorAddrequest), HttpStatus.OK);
    }

    @GetMapping(
            value = "/chargingsessions",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ChargingSessionEntity>> getChargingSessions(@RequestParam(value = "from", required = false) String dateFrom,
                                                                                 @RequestParam(value = "to", required = false) String dateTo
    ) {
        log.info("ADMIN_CONTROLLER: Get chargingsession by: from {}, to {}", dateFrom, dateTo);
        try {
            ChargingSessionFilter sessionFilter = new ChargingSessionFilter(dateFrom, dateTo);
            return new ResponseEntity<>(chargingSessionService.findAllByTimeBetween(sessionFilter), HttpStatus.OK);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
