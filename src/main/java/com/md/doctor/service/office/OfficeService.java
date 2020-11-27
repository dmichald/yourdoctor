package com.md.doctor.service.office;

import com.md.doctor.dto.office.GetOfficeDto;
import com.md.doctor.dto.office.OfficeDetails;
import com.md.doctor.dto.office.OfficeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OfficeService {

    void saveOffice(OfficeDto officeDto, Long ownerId);

    Page<GetOfficeDto> getOfficesByDoctorSpecializationId(Long specializationId, Pageable pageable);

    List<String> getCities();

    Page<GetOfficeDto> findByNameOrSurnameAndCityAndSpecialization(String name, String city, Long specializationId, Pageable pageable);

    OfficeDetails getById(Long id);

}
