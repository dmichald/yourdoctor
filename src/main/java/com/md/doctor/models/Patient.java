package com.md.doctor.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String telephoneNumber;
    @OneToOne
    private Address address;
    @OneToOne(mappedBy = "patient")
    private Reservation reservation;

}
