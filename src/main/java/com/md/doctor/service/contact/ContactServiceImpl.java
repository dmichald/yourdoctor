package com.md.doctor.service.contact;

import com.md.doctor.dto.ContactDto;
import com.md.doctor.exception.EntityNotFoundException;
import com.md.doctor.mapper.ContactMapper;
import com.md.doctor.repository.ContactRepo;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

    private ContactMapper contactMapper = Mappers.getMapper(ContactMapper.class);
    private final ContactRepo contactRepository;


    public ContactServiceImpl(ContactRepo contactRepository) {
        this.contactRepository = contactRepository;

    }

    @Override
    public void saveContact(ContactDto contactDto) {
        contactRepository.save(contactMapper.mapToContact(contactDto));

    }

    @Override
    public ContactDto getContactById(Long id) {
        return contactRepository.findById(id)
                .map(contactMapper::mapToContactDto)
                .orElseThrow(() -> new EntityNotFoundException("CONTACT WITH GIVEN ID NOT EXIST. ID: " + id));
    }
}
