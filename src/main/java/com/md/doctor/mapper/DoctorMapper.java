package com.md.doctor.mapper;

import com.md.doctor.dto.address.AddDoctorDto;
import com.md.doctor.dto.doctor.GetDoctorDto;
import com.md.doctor.models.Doctor;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper to map between Doctor and DoctorDto models
 */
@Mapper
public interface DoctorMapper {

    GetDoctorDto mapToGetDoctorDto(Doctor doctor);


    Doctor mapAddDoctorDtoToDoctor(AddDoctorDto doctorDto);

    AddDoctorDto mapToAddDoctorDto(GetDoctorDto getDoctorDto);

    Doctor mapToDoctor(GetDoctorDto getDoctorDto);

}
