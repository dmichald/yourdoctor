package com.md.doctor.dto.specilalization;

import com.md.doctor.dto.specilalization.SpecializationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SpecializationListDto {
    private List<SpecializationDto> specializations;

}
