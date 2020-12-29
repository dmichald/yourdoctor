package doctor.service.doctor;

import doctor.dto.doctor.AddDoctorDto;
import doctor.dto.doctor.GetDoctorDto;
import doctor.dto.doctor.UpdateDoctorNameAndSurnameDto;
import doctor.exception.EntityNotFoundException;
import doctor.mapper.DoctorMapper;
import doctor.models.Doctor;
import doctor.models.Specialization;
import doctor.repository.DoctorRepo;
import doctor.repository.SpecializationRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static doctor.exception.EntityNotFoundExceptionMessage.DOCTOR_NOT_FOUND;
import static doctor.exception.EntityNotFoundExceptionMessage.SPECIALIZATION_NOT_FOUND;

@RequiredArgsConstructor
@Service
@Slf4j
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
        Doctor saved = doctorRepository.save(doctor);

        log.debug("Saved doctor with id: " + saved.getId());
        return saved;
    }

    @Override
    @Transactional
    public void addSpecialization(Long specializationId, Long doctorId) {
        Doctor doctor = getDoctor(doctorId);
        Specialization specialization = getSpecialization(specializationId);
        doctor.addSpecialization(specialization);
        log.debug("Added specialization: " + specialization.getName() + " to doctor with id: " + doctorId);
    }

    @Transactional
    @Override
    public void removeSpecialization(Long specializationId, Long doctorId) {
        Doctor doctor = getDoctor(doctorId);
        Specialization specialization = getSpecialization(specializationId);
        doctor.removeSpecialization(specialization);

        log.debug("Removed specialization: " + specialization.getName() + " from doctor with id: " + doctorId);
    }

    @Transactional
    @Override
    public Doctor updateDoctorNameAndSurname(Long id, UpdateDoctorNameAndSurnameDto doctor) {
        Doctor doc = getDoctor(id);
        doc.setName(doctor.getName());
        doc.setSurname(doctor.getSurname());

        log.debug("Updated doctor name and surname. Doctor id: " + doc.getId());
        return doc;
    }


    private void addSpecializations(AddDoctorDto doctorDto, Doctor doctor) {
        if (doctorDto.getSpecializationSet() != null && !doctorDto.getSpecializationSet().isEmpty()) {
            doctorDto.getSpecializationSet().forEach(specId -> doctor.addSpecialization(getSpecialization(specId)));
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
