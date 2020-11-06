package com.md.doctor.controller;

import com.md.doctor.dto.ReservationDto;
import com.md.doctor.mapper.ReservationMapper;
import com.md.doctor.repository.ReservationRepo;
import com.md.doctor.service.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationRepo reservationRepo;
    private final ReservationMapper reservationMapper = Mappers.getMapper(ReservationMapper.class);

    @PostMapping("/offices/{officeId}/reservations")
    public void addReservation(@PathVariable Long officeId, @Valid @RequestBody ReservationDto reservationDto) {
        reservationService.saveReservation(reservationDto, officeId);
    }

    @GetMapping("/offices")
    List<ReservationDto> getAll() {
        return reservationRepo.findAll().stream()
                .map(reservationMapper::mapToReservationDto)
                .collect(Collectors.toList());
    }


}
