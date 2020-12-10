package doctor.service.contact;

import doctor.dto.contact.ContactDto;
import doctor.exception.EntityNotFoundException;
import doctor.mapper.ContactMapper;
import doctor.models.Contact;
import doctor.repository.ContactRepo;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static doctor.exception.EntityNotFoundExceptionMessage.CONTACT_NOT_FOUND;

@Service
@Slf4j
public class ContactServiceImpl implements ContactService {

    private final ContactRepo contactRepository;
    private ContactMapper contactMapper = Mappers.getMapper(ContactMapper.class);


    public ContactServiceImpl(ContactRepo contactRepository) {
        this.contactRepository = contactRepository;

    }

    @Override
    public void saveContact(ContactDto contactDto) {
        Contact contact = contactRepository.save(contactMapper.mapToContact(contactDto));
        log.debug("Added contact with id: " + contact.getId());
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
        log.debug("Updated contact with id: " + contact.getId());
    }
}
