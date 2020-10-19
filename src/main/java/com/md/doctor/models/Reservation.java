package com.md.doctor.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;


@Entity
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Office office;

    @OneToOne
    private Patient patient;

    private Date date;

    private Time startTime;

    private Time endTime;

    @CreationTimestamp
    private Date createdAt;

    private boolean canceled;

    public void addPatient(Patient patient) {
        if (patient != null) {
            this.patient = patient;
            patient.setReservation(this);
        }
    }

    public void addOffice(Office office) {
        if (office != null) {
            this.office = office;
            if (office.getReservations() != null) {
                office.getReservations().add(this);
            }
        }
    }
}
