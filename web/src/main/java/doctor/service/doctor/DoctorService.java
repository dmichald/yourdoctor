package doctor.service.doctor;

import doctor.dto.doctor.AddDoctorDto;
import doctor.dto.doctor.GetDoctorDto;
import doctor.dto.doctor.UpdateDoctorNameAndSurnameDto;
import doctor.models.Doctor;
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

    Doctor updateDoctorNameAndSurname(Long id, UpdateDoctorNameAndSurnameDto doctor);


}
