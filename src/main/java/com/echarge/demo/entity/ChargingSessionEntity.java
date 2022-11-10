package com.echarge.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "chargingsession")
public class ChargingSessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "starttime", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(name = "startMeter")
    private Double startMeter;

    @Column(name = "endtime", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Column(name = "endMeter")
    private Double endMeter;

    private String message;

    @JsonIgnore
    @Column(name = "customerId")
    private Long customerId;

    @JsonIgnore
    @Column(name = "connectorid")
    private Long connectorId;

    @JsonIgnore
    @Column(name = "vehicleid")
    private Long vehicleId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customerId", referencedColumnName = "id", insertable = false, updatable = false)
    private CustomerEntity customer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "connectorid", referencedColumnName = "id", insertable = false, updatable = false)
    private ConnectorEntity connector;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "vehicleid", referencedColumnName = "id", insertable = false, updatable = false)
    private VehicleEntity vehicle;
}
