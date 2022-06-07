package com.cpilosenlaces.microservice.model.disheap;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "events")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Event {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;
    @Column
    private String name;
    @Column
    private String note;
    @Column(name = "start_date")
    private long startDate;
    @Column(name = "end_date")
    private long endDate;
    @Column
    private String type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user-event")
    private UserModel user;
}
