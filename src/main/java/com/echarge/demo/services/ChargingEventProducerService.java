package com.echarge.demo.services;

import com.echarge.demo.payload.request.ChargingEventRequest;
import com.echarge.demo.payload.response.ChargingRequestResponse;

public interface ChargingEventProducerService {
    ChargingRequestResponse startChargingEvent(long userId, ChargingEventRequest request) throws IllegalAccessException;

    ChargingRequestResponse endChargingEvent(long userId, ChargingEventRequest request) throws IllegalAccessException;
}
