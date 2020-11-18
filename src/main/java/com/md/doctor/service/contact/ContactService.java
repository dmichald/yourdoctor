package com.md.doctor.service.contact;

import com.md.doctor.dto.contact.ContactDto;

public interface ContactService {
    void saveContact(ContactDto contactDto);

    ContactDto getContactById(Long id);

    void updateContact(ContactDto contactDto);
}
