package com.md.doctor.dto.office;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.md.doctor.dto.LocalTimeDeserializer;
import com.md.doctor.dto.address.AddressDto;
import com.md.doctor.dto.contact.ContactDto;
import com.md.doctor.dto.doctor.GetDoctorDto;
import com.md.doctor.dto.reseravtion.ReservationDto;
import com.md.doctor.models.security.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
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

    @Min(value = 1L, message = "Price cannot be less than 1")
    @Max(value = 1000L, message = "Price for visit cannot be bigger than 1000")
    int price;
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    LocalTime startWorkAt;
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    LocalTime finishWorkAt;

    @Min(value = 10L, message = "Visit duration cannot be shorter than 10 minutes")
    @Max(value = 60L, message = "Visit duration cannot be longer than 60 minutes")
    int oneVisitDuration;
}
