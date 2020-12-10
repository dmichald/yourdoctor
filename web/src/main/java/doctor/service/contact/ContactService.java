package doctor.service.contact;


import doctor.dto.contact.ContactDto;

public interface ContactService {
    void saveContact(ContactDto contactDto);

    ContactDto getContactById(Long id);

    void updateContact(ContactDto contactDto);
}
