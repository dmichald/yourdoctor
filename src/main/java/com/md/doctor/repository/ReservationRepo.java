package com.md.doctor.repository;

import com.md.doctor.models.Office;
import com.md.doctor.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByOfficeAndDate(Office office, Date date);

}
