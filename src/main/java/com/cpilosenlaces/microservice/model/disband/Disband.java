package com.cpilosenlaces.microservice.model.disband;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "disbands")
@AllArgsConstructor
@NoArgsConstructor
public class Disband {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;
    @Column
    private String mac;
    @Column
    private String model;
    @Column
    private String version;
    @Column
    private long date;

    @Column
    private UUID userId;
}
