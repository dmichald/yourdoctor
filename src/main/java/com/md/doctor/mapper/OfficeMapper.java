package com.md.doctor.mapper;

import com.md.doctor.dto.OfficeDto;
import com.md.doctor.models.Office;
import org.mapstruct.Mapper;

import java.sql.Time;
import java.time.LocalTime;

@Mapper
public interface OfficeMapper {
    Office mapToOffice(OfficeDto officeDto);

    OfficeDto mapToOfficeDto(Office office);

    default LocalTime map(Time time){
        return  time.toLocalTime();
    }
    default Time map(LocalTime localTime){
        return  Time.valueOf(localTime);
    }

}
