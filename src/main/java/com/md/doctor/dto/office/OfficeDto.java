package com.md.doctor.dto.office;


import com.md.doctor.dto.reseravtion.ReservationDto;
import com.md.doctor.dto.address.AddressDto;
import com.md.doctor.dto.contact.ContactDto;
import com.md.doctor.dto.doctor.GetDoctorDto;
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
public class OfficeDto {
    private Long id;

    @NotNull
    private GetDoctorDto doctor;
    @NotNull
    private ContactDto contact;
    @NotNull
    AddressDto address;
    @NotNull
    private Set<ReservationDto> reservations = new HashSet<>();
    private User owner;
}
