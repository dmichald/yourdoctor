package com.md.doctor.service.contact;

import com.md.doctor.dto.ContactDto;

public interface ContactService {
    void saveContact(ContactDto contactDto);

    ContactDto getContactById(Long id);
}
