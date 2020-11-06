package com.md.doctor.models;

import com.md.doctor.models.security.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Office office = (Office) o;
        return Objects.equals(id, office.id) &&
                Objects.equals(doctor, office.doctor) &&
                Objects.equals(contact, office.contact) &&
                Objects.equals(address, office.address) &&
                Objects.equals(owner, office.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
