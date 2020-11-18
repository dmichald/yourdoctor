package com.md.doctor.service.reservation;

import com.md.doctor.dto.reseravtion.ReservationDto;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

public interface ReservationService {
    void saveReservation(@Valid ReservationDto reservationDto, Long officeId);

    ReservationDto getReservationById(Long id);

    void cancelReservation(Long reservationId);

    /**
     * Returns list of strings that represents available hours for reservations in given office in given date
     * @param date the date in which will be searching available hours
     * @param officeId the office in which hours should be searching
     * @return list off free hours in given office in given date
     */
    List<String> getFreeReservationsHours(Date date, Long officeId);

}
