package com.md.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import java.sql.Date;
import java.sql.Time;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
 public class ReservationDto {

    private Long id;
    private PatientDto patient;

    @FutureOrPresent
    private Date date;

    @FutureOrPresent
    private Time startTime;

    @Future
    private Time endTime;

    private boolean canceled;

}
