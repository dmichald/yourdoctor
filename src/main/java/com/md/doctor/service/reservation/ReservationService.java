package com.md.doctor.service.reservation;

import com.md.doctor.dto.reseravtion.ReservationDto;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ReservationService {
    void saveReservation(@Valid ReservationDto reservationDto, Long officeId);

    ReservationDto getReservationById(Long id);

    @Secured("ROLE_DOCTOR")
    @PostAuthorize("@reservationRepo.getOne(#reservationId).office.owner.username == authentication.name")
    void cancelReservation(Long reservationId);

    /**
     * Returns list of strings that represents available hours for reservations in given office in given date
     * @param date the date in which will be searching available hours
     * @param officeId the office in which hours should be searching
     * @return list off free hours in given office in given date
     */
    List<String> getFreeReservationsHours(Date date, Long officeId);

    /**
     *
     * @param from LocalDate start date of reservations
     * @param to number of days added to start date, created in this way date is ending date
     * @param officeId office which  reservations is assigned
     * @return returns reservations group by date, sort asc
     */
    Map<LocalDate, List<ReservationDto>> getReservationFromTo(LocalDate from, int to, Long officeId);

}
