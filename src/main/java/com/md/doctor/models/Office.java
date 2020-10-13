package com.md.doctor.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String email;
    @OneToOne
    Doctor doctor;
    @OneToOne
    Contact contact;
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER, mappedBy = "office")
    Set<Reservation> reservations = new HashSet<>();
    @OneToOne
    User owner;
}
