package com.md.doctor.models;

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

public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "doctor_specialization",
            joinColumns = {@JoinColumn(name = "DOCTOR_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "SPECIALIZATION_ID", referencedColumnName = "ID")})
    private Set<Specialization> specializations = new HashSet<>();

}
