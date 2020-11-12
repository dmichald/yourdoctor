package com.md.doctor.service.office;

import com.md.doctor.dto.GetOfficeDto;
import com.md.doctor.dto.OfficeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OfficeService {

    void saveOffice(OfficeDto officeDto);

    Page<GetOfficeDto> getOfficesByDoctorSpecializationId(Long specializationId, Pageable pageable);

    List<String> getCities();

}
