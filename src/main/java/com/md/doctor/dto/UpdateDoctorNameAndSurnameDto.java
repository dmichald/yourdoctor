package com.md.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class UpdateDoctorNameAndSurnameDto {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
}
