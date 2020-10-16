package com.md.doctor.service.doctor;

import com.md.doctor.dto.AddDoctorDto;
import com.md.doctor.dto.GetDoctorDto;

import com.md.doctor.exception.EntityNotFoundException;
import com.md.doctor.mapper.DoctorMapper;
import com.md.doctor.models.Doctor;
import com.md.doctor.repository.DoctorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DoctorServiceImpl implements DoctorService {

    private static String ENTITY_NOT_FOUND = "ENTITY WITH GIVEN ID NOT EXIST. ID: ";
    private final DoctorRepo doctorRepository;
    private DoctorMapper doctorMapper = DoctorMapper.INSTANCE;

    @Override
    public GetDoctorDto getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .map(doctor -> doctorMapper.mapToGetDoctorDto(doctor))
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND + id));
    }

    @Override
    public Page<GetDoctorDto> getDoctorsByCategory(Long specializationId, Pageable pageable) {

        Page<Doctor> entities = doctorRepository.findBySpecializationId(specializationId, pageable);
        return entities
                .map(doctor -> doctorMapper.mapToGetDoctorDto(doctor));
    }

    @Override
    public Page<GetDoctorDto> getDoctorByNameOrSurname(String name, String surname, Pageable pageable) {
        return doctorRepository.findAllByNameContainingOrSurnameContaining(name, surname, pageable)
                .map(doctor -> doctorMapper.mapToGetDoctorDto(doctor));
    }

    @Override
    @Transactional
    public void saveDoctor(AddDoctorDto doctorDto) {
        doctorRepository.save(doctorMapper.mapAddDoctorDtoToDoctor(doctorDto));
    }

}
