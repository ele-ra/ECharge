package com.echarge.demo.payload.response;

import com.echarge.demo.entity.ChargingSessionEntity;
import com.echarge.demo.payload.request.ChargingEventRequest;
import lombok.Data;

@Data
public class ChargingRequestResponse {

    private ChargingEventRequest request;
    private MessageResponse message;
    private ChargingSessionEntity chargingSession;
}
