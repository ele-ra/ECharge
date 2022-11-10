package com.echarge.demo.payload.request;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

@Data
public class ConnectorAddRequest {
    @NotBlank
    private String chargePointName;

    @NotBlank
    private String chargePointSerialNumber;

    @Digits(integer = 5, fraction = 0, message = "Invalid request")
    private Integer connectorNumber;
}
