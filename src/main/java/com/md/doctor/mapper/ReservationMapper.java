package com.md.doctor.mapper;

import com.md.doctor.dto.ReservationDto;
import com.md.doctor.models.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ReservationMapper {
    @Mapping(target = "createdAt", ignore = true)
    Reservation mapToReservation(ReservationDto reservationDto);

    ReservationDto mapToReservationDto(Reservation reservation);
}
