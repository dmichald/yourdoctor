package com.md.doctor.service.doctor;

import com.md.doctor.dto.AddDoctorDto;
import com.md.doctor.dto.GetDoctorDto;
import com.md.doctor.models.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface DoctorService {
    GetDoctorDto getDoctorById(Long id);

    Page<GetDoctorDto> getDoctorsByCategory(Long specializationId, Pageable pageable);

    Page<GetDoctorDto> getDoctorByNameOrSurname(String name, String surname, Pageable pageable);

    Doctor saveDoctor(AddDoctorDto doctorDto);

    void addSpecialization(Long specializationId, Long doctorId);
    void removeSpecialization(Long specializationId, Long doctorId);


}
