package com.md.doctor.mapper;

import com.md.doctor.dto.doctordto.AddDoctorDto;
import com.md.doctor.dto.doctordto.GetDoctorDto;
import com.md.doctor.models.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper to map between Doctor and DoctorDto models
 */
@Mapper
public interface DoctorMapper {
    DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);

    GetDoctorDto mapToGetDoctorDto(Doctor doctor);

    @Mapping(target = "id", ignore = true)
    Doctor mapAddDoctorDtoToDoctor(AddDoctorDto doctorDto);

}
