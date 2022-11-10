package com.echarge.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "chargepoint")
public class ChargePointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "sn", unique = true)
    private String sn;

    @Column(name = "customerid")
    private Long customerId;

    @Override
    public boolean equals(Object that) {
        if (this == that)
            return true;
        if (!(that instanceof ChargePointEntity))
            return false;

        ChargePointEntity toCompare = (ChargePointEntity) that;
        return this.getName().equals((toCompare).getName()) && this.getSn().equals(toCompare.getSn())
                && this.getCustomerId().equals(toCompare.getCustomerId());
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, sn, customerId);
    }
}

