package doctor.models;

import doctor.models.security.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
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
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY, mappedBy = "office")
    private Set<Reservation> reservations = new HashSet<>();
    @OneToOne
    private User owner;
    private int price;
    private Time startWorkAt;
    private Time finishWorkAt;
    private int oneVisitDuration;
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
