package com.cpilosenlaces.disheap_backend.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.GenericGenerator;

import io.swagger.v3.oas.annotations.Parameter;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disband")
    @JsonBackReference(value = "disband-measures")
    @Parameter(hidden = true)
    private List<Measure> measures;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disband")
    @JsonBackReference(value = "disband-alarms")
    @Parameter(hidden = true)
    private List<Alarm> alarms;
}
