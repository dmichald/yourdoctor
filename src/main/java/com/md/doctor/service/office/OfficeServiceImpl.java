package com.md.doctor.service.office;

import com.md.doctor.dto.GetOfficeDto;
import com.md.doctor.dto.OfficeDto;
import com.md.doctor.exception.EntityNotFoundException;
import com.md.doctor.mapper.AddressMapper;
import com.md.doctor.mapper.ContactMapper;
import com.md.doctor.mapper.DoctorMapper;
import com.md.doctor.mapper.OfficeMapper;
import com.md.doctor.models.*;
import com.md.doctor.models.security.User;
import com.md.doctor.repository.AddressRepo;
import com.md.doctor.repository.ContactRepo;
import com.md.doctor.repository.OfficeRepo;
import com.md.doctor.repository.SpecializationRepo;
import com.md.doctor.service.doctor.DoctorService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OfficeServiceImpl implements OfficeService {
    private final OfficeRepo officeRepository;
    private final DoctorService doctorService;
    private final ContactRepo contactRepo;
    private final AddressRepo addressRepo;
    private final SpecializationRepo specializationRepo;

    private AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);
    private ContactMapper contactMapper = Mappers.getMapper(ContactMapper.class);
    private DoctorMapper doctorMapper = Mappers.getMapper(DoctorMapper.class);
    private OfficeMapper officeMapper = Mappers.getMapper(OfficeMapper.class);


    @Override
    @Transactional
    public void saveOffice(OfficeDto officeDto) {

        Doctor doctor = doctorService.saveDoctor(doctorMapper.mapToAddDoctorDto(officeDto.getDoctor()));
        Contact contact = contactRepo.save(contactMapper.mapToContact(officeDto.getContact()));
        Address address = addressRepo.save(addressMapper.mapAddressDtoToAddress(officeDto.getAddress()));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Office office = new Office();
        office.setOwner(user);
        office.setAddress(address);
        office.setContact(contact);
        office.setDoctor(doctor);
        officeRepository.save(office);

    }

    @Override
    public Page<GetOfficeDto> getOfficesByDoctorSpecializationId(Long specializationId, Pageable pageable) {
        Specialization specialization = specializationRepo.findById(specializationId)
                .orElseThrow(() -> new EntityNotFoundException("SPECIALIZATION WITH GIVEN ID NOT EXIST. ID: " + specializationId));


        return officeRepository.findAllByDoctor_Specializations(specialization, pageable)
                .map(office ->
                        GetOfficeDto.builder().id(office.getId())
                                .name(office.getDoctor().getName() + " " + office.getDoctor().getSurname())
                                .specializationName(specialization.getName())
                                .build());
    }
}
