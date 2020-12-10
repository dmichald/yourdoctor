package doctor.service.office;

import doctor.dto.office.AddOfficeDto;
import doctor.dto.office.GetOfficeDto;
import doctor.dto.office.OfficeDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OfficeService {

    void saveOffice(AddOfficeDto officeDto, Long ownerId);

    Page<GetOfficeDto> getOfficesByDoctorSpecializationId(Long specializationId, Pageable pageable);

    List<String> getCities();

    Page<GetOfficeDto> findByNameOrSurnameAndCityAndSpecialization(String name, String city, Long specializationId, Pageable pageable);

    OfficeDetails getById(Long id);

}
