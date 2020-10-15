package com.md.doctor.service.doctor;

import com.md.doctor.dto.doctordto.AddDoctorDto;
import com.md.doctor.dto.doctordto.GetDoctorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface DoctorService {
    GetDoctorDto getDoctorById(Long id);

    Page<GetDoctorDto> getDoctorsByCategory(Long specializationId, Pageable pageable);

    Page<GetDoctorDto> getDoctorByNameOrSurname(String name, String surname, Pageable pageable);

    void saveDoctor(AddDoctorDto doctorDto);


}
