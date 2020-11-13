package com.md.doctor.controller;

import com.md.doctor.dto.ReservationDto;
import com.md.doctor.service.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/offices/{officeId}/reservations")
    public void addReservation(@PathVariable Long officeId, @Valid @RequestBody ReservationDto reservationDto) {
        reservationService.saveReservation(reservationDto, officeId);
    }
}
