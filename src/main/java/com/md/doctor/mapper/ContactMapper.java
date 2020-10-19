package com.md.doctor.mapper;

import com.md.doctor.dto.ContactDto;
import com.md.doctor.models.Contact;
import org.mapstruct.Mapper;

@Mapper
public interface ContactMapper {
    Contact mapToContact(ContactDto contactDto);

    ContactDto mapToContactDto(Contact contact);
}
