package com.echarge.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "rfidtag")
public class RFIDTagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "number", unique = true)
    private Integer number;

    @Column(name = "customerId")
    private Long customerId;

    @Column(name = "vehicleid")
    private Long vehicleId;

    @Override
    public boolean equals(Object that) {
        if (this == that)
            return true;
        if (!(that instanceof RFIDTagEntity))
            return false;

        RFIDTagEntity toCompare = (RFIDTagEntity) that;
        return this.getNumber().equals(toCompare.getNumber()) && this.getCustomerId().equals(toCompare.getCustomerId())
                && this.getVehicleId().equals(toCompare.getVehicleId());
    }


    @Override
    public int hashCode() {
        return Objects.hash(number, customerId, vehicleId);
    }
}
