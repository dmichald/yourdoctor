package doctor.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;


@Entity
@Setter
@Getter
@NoArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return canceled == that.canceled &&
                Objects.equals(id, that.id) &&
                Objects.equals(office, that.office) &&
                Objects.equals(patient, that.patient) &&
                Objects.equals(date, that.date) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startTime, createdAt, canceled);
    }
}
