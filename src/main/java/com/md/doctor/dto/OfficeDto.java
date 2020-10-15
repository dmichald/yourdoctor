package com.md.doctor.dto;

import com.md.doctor.dto.doctordto.GetDoctorDto;
import com.md.doctor.models.security.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class OfficeDto {
    private Long id;

    @NotNull
    private GetDoctorDto doctor;
    @NotNull
    private ContactDto contact;
    @NotNull
    private Set<ReservationDto> reservations = new HashSet<>();
    private User owner;
}
