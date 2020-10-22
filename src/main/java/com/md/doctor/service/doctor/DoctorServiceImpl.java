package com.md.doctor.service.doctor;

import com.md.doctor.dto.AddDoctorDto;
import com.md.doctor.dto.GetDoctorDto;
import com.md.doctor.exception.EntityNotFoundException;
import com.md.doctor.mapper.DoctorMapper;
import com.md.doctor.mapper.SpecializationMapper;
import com.md.doctor.models.Doctor;
import com.md.doctor.repository.DoctorRepo;
import com.md.doctor.repository.SpecializationRepo;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DoctorServiceImpl implements DoctorService {

    private static String ENTITY_NOT_FOUND = "ENTITY WITH GIVEN ID NOT EXIST. ID: ";
    private final DoctorRepo doctorRepository;
    private final SpecializationRepo specializationRepo;
    private DoctorMapper doctorMapper = Mappers.getMapper(DoctorMapper.class);
    private SpecializationMapper specializationMapper = Mappers.getMapper(SpecializationMapper.class);

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
    public Doctor saveDoctor(AddDoctorDto doctorDto) {
        Doctor doctor = new Doctor();
        doctor.setName(doctorDto.getName());
        doctor.setSurname(doctorDto.getSurname());

        addSpecializations(doctorDto, doctor);
        return doctorRepository.save(doctor);
    }



    private void addSpecializations(AddDoctorDto doctorDto, Doctor doctor) {
        if(doctorDto.getSpecializationSet() != null && !doctorDto.getSpecializationSet().isEmpty()){
            doctorDto.getSpecializationSet().forEach(specializationDto ->
                    doctor.addSpecialization(
                            specializationRepo.findById(specializationDto.getId())
                                    .orElseThrow(() -> new EntityNotFoundException("SPECIALIZATION WITH ID: " + specializationDto.getId() + " NOT EXIST"))
                    )
            );
        }
    }


}
