package com.echarge.demo.payload.request;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

@Data
public class ChargingEventRequest {
    // Will only search by unique values (for charge point - sn, for rdfidtag - number)
    // Expected workflow: user enters name, gets list of all matching, from the list clarifies the exact item, choosing sn or number
    @NotBlank
    private String chargePointSerialNumber;

    @Digits(integer = 5, fraction = 0, message = "Invalid request")
    private Integer connectorNumber; // Assume user connects to a certain one

    @Digits(integer = 5, fraction = 0, message = "Invalid request")
    private Integer rdfidTagNumber;
}
