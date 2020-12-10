package doctor.mapper;

import doctor.dto.patient.PatientDto;
import doctor.models.Patient;
import org.mapstruct.Mapper;

@Mapper
public interface PatientMapper {

    Patient mapToPatient(PatientDto patientDto);

    PatientDto mapToPatientDto(Patient patient);
}
