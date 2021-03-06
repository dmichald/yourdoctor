package doctor.repository;

import doctor.models.Office;
import doctor.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByOfficeAndDate(Office office, Date date);

    List<Reservation> findAllByDateBetweenAndOffice(Date start, Date end, Office office);

}
