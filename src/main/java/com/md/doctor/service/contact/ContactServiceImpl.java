package com.md.doctor.service.contact;

import com.md.doctor.dto.ContactDto;
import com.md.doctor.exception.EntityNotFoundException;
import com.md.doctor.mapper.ContactMapper;
import com.md.doctor.models.Contact;
import com.md.doctor.repository.ContactRepo;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.md.doctor.exception.EntityNotFoundExceptionMessage.CONTACT_NOT_FOUND;

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
                .orElseThrow(() -> new EntityNotFoundException(CONTACT_NOT_FOUND(id)));
    }

    @Override
    @Transactional
    public void updateContact(ContactDto contactDto) {
        Contact contact = contactRepository.findById(contactDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(CONTACT_NOT_FOUND(contactDto.getId())));

        BeanUtils.copyProperties(contactDto, contact);
    }
}
