package doctor.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String telephoneNumber;

    @OneToOne(mappedBy = "patient")
    private Reservation reservation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id) &&
                Objects.equals(email, patient.email) &&
                Objects.equals(name, patient.name) &&
                Objects.equals(surname, patient.surname) &&
                Objects.equals(telephoneNumber, patient.telephoneNumber) &&
                Objects.equals(reservation, patient.reservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, telephoneNumber, reservation, name, surname);
    }
}
