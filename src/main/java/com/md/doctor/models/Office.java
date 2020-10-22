package com.md.doctor.models;

import com.md.doctor.models.security.User;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Doctor doctor;
    @OneToOne
    private Contact contact;
    @OneToOne
    private Address address;
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER, mappedBy = "office")
    private Set<Reservation> reservations = new HashSet<>();
    @OneToOne
    private User owner;
}
