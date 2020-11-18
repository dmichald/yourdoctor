package com.md.doctor.dto.patient;

import com.md.doctor.dto.address.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PatientDto {
    private Long id;
    private String email;
    private String telephoneNumber;
    @NotNull
    private AddressDto address;
}
