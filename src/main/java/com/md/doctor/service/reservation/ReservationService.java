package com.md.doctor.service.reservation;

import com.md.doctor.dto.ReservationDto;

public interface ReservationService {
    void saveReservation(ReservationDto reservationDto);

    ReservationDto getReservationById(Long id);

    void cancelReservation(Long reservationId);

}
