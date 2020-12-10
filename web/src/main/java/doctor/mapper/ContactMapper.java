package doctor.mapper;

import doctor.dto.contact.ContactDto;
import doctor.models.Contact;
import org.mapstruct.Mapper;

@Mapper
public interface ContactMapper {
    Contact mapToContact(ContactDto contactDto);

    ContactDto mapToContactDto(Contact contact);
}
