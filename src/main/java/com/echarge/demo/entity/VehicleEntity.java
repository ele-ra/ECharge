package com.echarge.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "vehicle")
public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "registrationplate", unique = true)
    private String registrationPlate;

    private Double meter; // Treat this as a total charging indicator
}