package com.echarge.demo.controllers;

import com.echarge.demo.entity.UserEntity;
import com.echarge.demo.payload.request.ChargingEventRequest;
import com.echarge.demo.payload.response.ChargingRequestResponse;
import com.echarge.demo.services.ChargingEventProducerService;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    AuthenticationManager authenticationManager;

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private final ChargingEventProducerService chargingEventProducerService;

    @Autowired
    public CustomerController(ChargingEventProducerService chargingEventProducerService) {
        this.chargingEventProducerService = chargingEventProducerService;
    }

    @Transactional
    @PostMapping(
            value = "/charge/start",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChargingRequestResponse> startChargingSession(@Valid @RequestBody ChargingEventRequest chargingEventRequest, Authentication auth) {

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Long userId = ((UserEntity) userDetails).getId();
        log.info("CUSTOMER_CONTROLLER: Charging event requested by user = {}", userDetails.getUsername());

        try {
            return new ResponseEntity<>(chargingEventProducerService.startChargingEvent(userId, chargingEventRequest), HttpStatus.OK);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Transactional
    @PostMapping(
            value = "/charge/end",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChargingRequestResponse> endChargingSession(@Valid @RequestBody ChargingEventRequest chargingEventRequest, Authentication auth) {

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Long userId = ((UserEntity) userDetails).getId();
        log.info("CUSTOMER_CONTROLLER: Charging event requested by user = {}, request = {}", userDetails.getUsername(), chargingEventRequest);

        try {
            return new ResponseEntity<>(chargingEventProducerService.endChargingEvent(userId, chargingEventRequest), HttpStatus.OK);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
