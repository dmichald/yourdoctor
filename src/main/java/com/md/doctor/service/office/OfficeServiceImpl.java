package com.md.doctor.service.office;

import com.md.doctor.dto.office.AddOfficeDto;
import com.md.doctor.dto.office.GetOfficeDto;
import com.md.doctor.dto.office.OfficeDetails;
import com.md.doctor.exception.EntityNotFoundException;
import com.md.doctor.exception.EntityNotFoundExceptionMessage;
import com.md.doctor.mapper.AddressMapper;
import com.md.doctor.mapper.ContactMapper;
import com.md.doctor.mapper.DoctorMapper;
import com.md.doctor.mapper.OfficeMapper;
import com.md.doctor.models.*;
import com.md.doctor.models.security.User;
import com.md.doctor.repository.*;
import com.md.doctor.service.doctor.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.List;

import static com.md.doctor.exception.EntityNotFoundExceptionMessage.OFFICE_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class OfficeServiceImpl implements OfficeService {
    private final OfficeRepo officeRepository;
    private final DoctorService doctorService;
    private final ContactRepo contactRepo;
    private final AddressRepo addressRepo;
    private final SpecializationRepo specializationRepo;
    private final UserRepo userRepository;

    private AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);
    private ContactMapper contactMapper = Mappers.getMapper(ContactMapper.class);
    private DoctorMapper doctorMapper = Mappers.getMapper(DoctorMapper.class);
    private OfficeMapper officeMapper = Mappers.getMapper(OfficeMapper.class);


    @Override
    @Transactional
    public void saveOffice(AddOfficeDto officeDto, Long ownerId) {

        Doctor doctor = doctorService.saveDoctor(officeDto.getDoctor());
        Contact contact = contactRepo.save(contactMapper.mapToContact(officeDto.getContact()));
        Address address = addressRepo.save(addressMapper.mapAddressDtoToAddress(officeDto.getAddress()));
        User user = userRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException(EntityNotFoundExceptionMessage.USER_NOT_FOUND(ownerId)));

        Office office = new Office();
        office.setOwner(user);
        office.setAddress(address);
        office.setContact(contact);
        office.setDoctor(doctor);
        office.setOneVisitDuration(officeDto.getOneVisitDuration());
        office.setStartWorkAt(Time.valueOf(officeDto.getStartWorkAt()));
        office.setFinishWorkAt(Time.valueOf(officeDto.getFinishWorkAt()));
        office.setPrice(officeDto.getPrice());

        Office saved = officeRepository.save(office);
        log.debug("Saved office with id: " + saved.getId());

    }

    @Override
    public Page<GetOfficeDto> getOfficesByDoctorSpecializationId(Long specializationId, Pageable pageable) {
        Specialization specialization = specializationRepo.findById(specializationId)
                .orElseThrow(() -> new EntityNotFoundException(EntityNotFoundExceptionMessage.SPECIALIZATION_NOT_FOUND(specializationId)));


        return officeRepository.findAllByDoctor_Specializations(specialization, pageable)
                .map(office ->
                        GetOfficeDto.builder().id(office.getId())
                                .name(office.getDoctor().getName() + " " + office.getDoctor().getSurname())
                                .specializationName(specialization.getName())
                                .build());
    }

    @Override
    public List<String> getCities() {
        return officeRepository.getCities();
    }

    @Override
    public Page<GetOfficeDto> findByNameOrSurnameAndCityAndSpecialization(String name, String city, Long specializationId, Pageable pageable) {
        return officeRepository.
                getOffices(city, name, specializationId, pageable)
                .map(office ->
                        GetOfficeDto.builder().id(Long.valueOf(office.getId()))
                                .name(office.getFullName())
                                .specializationName(office.getSpecialization())
                                .build());
    }

    @Override
    public OfficeDetails getById(Long id) {
        return officeRepository.findById(id)
                .map(office -> officeMapper.mapToOfficeDetails(office))
                .orElseThrow(() -> new EntityNotFoundException(OFFICE_NOT_FOUND(id)));
    }
}
