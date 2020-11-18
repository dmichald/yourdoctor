package com.md.doctor.service.doctor;

import com.md.doctor.dto.AddDoctorDto;
import com.md.doctor.dto.GetDoctorDto;
import com.md.doctor.dto.UpdateDoctorNameAndSurnameDto;
import com.md.doctor.exception.EntityNotFoundException;
import com.md.doctor.mapper.DoctorMapper;
import com.md.doctor.models.Doctor;
import com.md.doctor.models.Specialization;
import com.md.doctor.repository.DoctorRepo;
import com.md.doctor.repository.SpecializationRepo;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.md.doctor.exception.EntityNotFoundExceptionMessage.DOCTOR_NOT_FOUND;
import static com.md.doctor.exception.EntityNotFoundExceptionMessage.SPECIALIZATION_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepo doctorRepository;
    private final SpecializationRepo specializationRepo;
    private DoctorMapper doctorMapper = Mappers.getMapper(DoctorMapper.class);

    @Override
    public GetDoctorDto getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .map(doctor -> doctorMapper.mapToGetDoctorDto(doctor))
                .orElseThrow(() -> new EntityNotFoundException(DOCTOR_NOT_FOUND(id)));
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

    @Override
    @Transactional
    public void addSpecialization(Long specializationId, Long doctorId) {
        Doctor doctor = getDoctor(doctorId);
        Specialization specialization = getSpecialization(specializationId);
        doctor.addSpecialization(specialization);
    }

    @Transactional
    @Override
    public void removeSpecialization(Long specializationId, Long doctorId) {
        Doctor doctor = getDoctor(doctorId);
        Specialization specialization = getSpecialization(specializationId);
        doctor.removeSpecialization(specialization);
    }

    @Transactional
    @Override
    public Doctor updateDoctorNameAndSurname(Long id, UpdateDoctorNameAndSurnameDto doctor) {
        Doctor doc = getDoctor(id);
        doc.setName(doctor.getName());
        doc.setSurname(doctor.getSurname());
        return doc;
    }


    private void addSpecializations(AddDoctorDto doctorDto, Doctor doctor) {
        if(doctorDto.getSpecializationSet() != null && !doctorDto.getSpecializationSet().isEmpty()){
            doctorDto.getSpecializationSet().forEach(specializationDto ->
                    doctor.addSpecialization(
                            getSpecialization(specializationDto.getId()))
            );
        }
    }

    private Doctor getDoctor(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(DOCTOR_NOT_FOUND(id)));
    }

    private Specialization getSpecialization(Long specializationId) {
        return specializationRepo.findById(specializationId)
                .orElseThrow(() -> new EntityNotFoundException(SPECIALIZATION_NOT_FOUND(specializationId)));
    }


}
