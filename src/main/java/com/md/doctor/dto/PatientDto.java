package com.md.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class PatientDto {
    private Long id;
    private String email;
    private String telephoneNumber;
    @NotNull
    private AddressDto address;
    @NotNull
    private ReservationDto reservation;
}
