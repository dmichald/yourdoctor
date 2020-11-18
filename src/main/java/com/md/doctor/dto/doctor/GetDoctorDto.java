package com.md.doctor.dto.doctor;

import com.md.doctor.dto.specilalization.SpecializationDto;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class GetDoctorDto {
    private Long id;

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String surname;

    private Set<SpecializationDto> specializations;
}
