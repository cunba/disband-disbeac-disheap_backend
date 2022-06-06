package com.cpilosenlaces.microservice.model.disbeac;

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
@Entity(name = "locations")
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;
    @Column
    private float latitude;
    @Column
    private float longitude;
    @Column
    private long date;

    @ManyToOne
    @JoinColumn(name = "disbeac_id")
    private Disbeac disbeac;
}
