package com.md.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class ReservationDto {

    private Long id;

    private OfficeDto office;
    private PatientDto patient;

    @FutureOrPresent
    private LocalDate date;

    @FutureOrPresent
    private LocalTime startTime;

    @Future
    private LocalTime endTime;

}
