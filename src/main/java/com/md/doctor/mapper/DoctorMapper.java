package com.md.doctor.mapper;

import com.md.doctor.dto.AddDoctorDto;
import com.md.doctor.dto.GetDoctorDto;
import com.md.doctor.models.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper to map between Doctor and DoctorDto models
 */
@Mapper
public interface DoctorMapper {
    DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);

    GetDoctorDto mapToGetDoctorDto(Doctor doctor);


    Doctor mapAddDoctorDtoToDoctor(AddDoctorDto doctorDto);

}
