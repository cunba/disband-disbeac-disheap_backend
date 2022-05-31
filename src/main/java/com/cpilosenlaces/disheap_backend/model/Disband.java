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
    private String name;
    @Column
    private String model;
    @Column(name = "firmware_version")
    private String firmwareVersion;
    @Column
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id_disbands_fk")
    private UserModel user;
}
