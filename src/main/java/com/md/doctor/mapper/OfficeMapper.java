package com.md.doctor.mapper;

import com.md.doctor.dto.OfficeDto;
import com.md.doctor.models.Office;
import org.mapstruct.Mapper;

@Mapper
public interface OfficeMapper {
    Office mapToOffice(OfficeDto officeDto);

    OfficeDto mapToOfficeDto(Office office);
}
