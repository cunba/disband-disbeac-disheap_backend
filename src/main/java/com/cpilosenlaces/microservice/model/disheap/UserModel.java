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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class UserModel {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String birthday;
    @Column(name = "is_disorder")
    private Boolean isDisorder;
    @Column
    private String email;
    @Column
    private String password;
    @Column(name = "created")
    private long created;
    @Column(name = "updated")
    private long updated;
    @Column
    private String role;

    @ManyToOne
    @JoinColumn(name = "disorder_id", nullable = true)
    private Disorder disorder;
    @ManyToOne
    @JoinColumn(name = "school_year_id", nullable = true)
    private SchoolYear schoolYear;
}
