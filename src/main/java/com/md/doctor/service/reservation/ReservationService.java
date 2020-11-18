package com.md.doctor.service.reservation;

import com.md.doctor.dto.ReservationDto;
import com.md.doctor.models.Office;

import javax.validation.Valid;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface ReservationService {
    void saveReservation(@Valid ReservationDto reservationDto, Long officeId);

    ReservationDto getReservationById(Long id);

    void cancelReservation(Long reservationId);

    List<ReservationDto> getReservationByDayAndDoctor(Date date, Office office);

    ReservationDto getReservationByDateAndStartTimeAndEndTime(Date date, Time startTime, Time endTime);

}
