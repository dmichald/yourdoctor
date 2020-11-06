package com.md.doctor.service.office;

import com.md.doctor.dto.GetOfficeDto;
import com.md.doctor.dto.OfficeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OfficeService {
    void saveOffice(OfficeDto officeDto);

    Page<GetOfficeDto> getOfficesByDoctorSpecializationId(Long specializationId, Pageable pageable);

}
