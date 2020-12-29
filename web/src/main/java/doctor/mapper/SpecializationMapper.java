package doctor.mapper;

import doctor.dto.specilalization.SpecializationDto;
import doctor.models.Specialization;

public interface SpecializationMapper {
    Specialization mapToSpecialization(SpecializationDto specializationDto);
}
