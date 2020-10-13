package com.md.doctor.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Office office;

    @OneToOne
    Patient patient;
    LocalDate date;
    LocalTime startTime;
    LocalTime endTime;

    @CreationTimestamp
    LocalDate createdAt;
}
