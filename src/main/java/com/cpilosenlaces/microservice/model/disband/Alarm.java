package com.cpilosenlaces.microservice.model.disband;

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
@Entity(name = "alarms")
@NoArgsConstructor
@AllArgsConstructor
public class Alarm {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;
    @Column
    private long date;
    @Column(name = "is_repetition")
    private Boolean isRepetition;
    @Column(name = "repetition_week_days", nullable = true)
    private String repetitionWeekDays;

    @ManyToOne
    @JoinColumn(name = "disband_id")
    private Disband disband;
}