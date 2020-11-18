package com.md.doctor.mapper;

import com.md.doctor.dto.patient.PatientDto;
import com.md.doctor.models.Patient;
import org.mapstruct.Mapper;

@Mapper
public interface PatientMapper {

    Patient mapToPatient(PatientDto patientDto);

    PatientDto mapToPatientDto(Patient patient);
}
