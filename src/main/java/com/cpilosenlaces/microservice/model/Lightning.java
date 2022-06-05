package com.cpilosenlaces.microservice.model;

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
@Entity(name = "lightning")
@NoArgsConstructor
@AllArgsConstructor
public class Lightning {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;
    @Column
    private int lightning;
    @Column(name = "red_lightning")
    private int redLightning;
    @Column(name = "green_lightning")
    private int greenLightning;
    @Column(name = "blue_lightning")
    private int blueLightning;
    @Column
    private long date;

    @ManyToOne
    @JoinColumn(name = "disband_id")
    private Disband disband;
}
