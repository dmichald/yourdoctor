package com.md.doctor.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "patient")
class Patient{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String email;
    String telephoneNumber;
    @OneToOne
    Address address;
    @OneToOne(mappedBy = "patient")
    Reservation reservation;

}
