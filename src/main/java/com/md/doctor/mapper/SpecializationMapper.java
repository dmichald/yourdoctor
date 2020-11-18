package com.md.doctor.mapper;

import com.md.doctor.dto.specilalization.SpecializationDto;
import com.md.doctor.models.Specialization;
import org.mapstruct.Mapper;

@Mapper
public interface SpecializationMapper {
    Specialization mapToSpecialization(SpecializationDto specializationDto);
}
