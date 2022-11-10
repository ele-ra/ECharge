package com.echarge.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "connector")
public class ConnectorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer number;

    @Column(name = "chargepointid")
    private Long chargePointId;

    @Override
    public boolean equals(Object that) {
        if (this == that)
            return true;
        if (!(that instanceof ConnectorEntity))
            return false;

        ConnectorEntity toCompare = (ConnectorEntity) that;
        return this.getNumber().equals((toCompare).getNumber()) && this.getChargePointId().equals(toCompare.getChargePointId());
    }


    @Override
    public int hashCode() {
        return Objects.hash(number, chargePointId);
    }
}
