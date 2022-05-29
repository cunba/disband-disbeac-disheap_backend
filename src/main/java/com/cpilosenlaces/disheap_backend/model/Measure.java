package com.cpilosenlaces.disheap_backend.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "measures")
@NoArgsConstructor
@AllArgsConstructor
public class Measure {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;
    @Column
    private float temperature;
    @Column
    private float humidity;
    @Column
    private float pressure;
    @Column(name = "ambient_noise")
    private float ambientNoise;
    @Column
    private float lightning;
    @Column(name = "red_lightning")
    private float redLightning;
    @Column(name = "green_lightning")
    private float greenLightning;
    @Column(name = "blue_lightning")
    private float blueLightning;
    @Column(name = "heart_rate")
    private float heartRate;
    @Column
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "disband_id_measures_fk")
    private Disband disband;
}
