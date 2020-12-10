package doctor.mapper;

import doctor.dto.doctor.AddDoctorDto;
import doctor.dto.doctor.GetDoctorDto;
import doctor.models.Doctor;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper to map between Doctor and DoctorDto doctor.models
 */
@Mapper
public interface DoctorMapper {

    GetDoctorDto mapToGetDoctorDto(Doctor doctor);


    Doctor mapAddDoctorDtoToDoctor(AddDoctorDto doctorDto);

    AddDoctorDto mapToAddDoctorDto(GetDoctorDto getDoctorDto);

    Doctor mapToDoctor(GetDoctorDto getDoctorDto);

}
