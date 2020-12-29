package doctor.service.contact;

import doctor.dto.contact.ContactDto;
import doctor.exception.EntityNotFoundException;
import doctor.repository.ContactRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static doctor.TestResource.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Disabled
@ExtendWith(SpringExtension.class)
@DisplayName("ContactServiceImpl should")
class ContactServiceImplTest {

    @MockBean
    private ContactRepo contactRepository;

    private ContactService contactService;

    @BeforeEach
    void setUp() {
        contactService = new ContactServiceImpl(contactRepository);
    }

    @DisplayName("correct save contact")
    @Test
    void saveContact() {
        //when
        contactService.saveContact(CONTACT_DTO);

        //then
        verify(contactRepository, times(1)).save(any());

    }

    @DisplayName("return contact by given id")
    @Test
    void getContactById() {
        when(contactRepository.findById(ID)).thenReturn(Optional.of(CONTACT));

        //when
        ContactDto returnedContact = contactService.getContactById(ID);

        //then
        assertEquals(CONTACT_DTO, returnedContact);
    }


    @Test
    @DisplayName(" should throw EntityNotFoundException when contact not exist")
    void shouldThrowEntityNOtFoundException() {
        //when
        when(contactRepository.findById(anyLong())).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> contactService.getContactById(anyLong()));
    }


    @Test
    @DisplayName("should correct update contact")
    void shouldUpdateContact() {
        //when
        when(contactRepository.findById(ID)).thenReturn(Optional.of(CONTACT));

        //then
        assertDoesNotThrow(() -> contactService.updateContact(CONTACT_DTO));

    }

    @Test
    @DisplayName(" should throw EntityNotFoundException during updating contact if it not exists")
    void shouldThrowExceptionDuringUpdatingContact() {
        //when
        when(contactRepository.findById(ID)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> contactService.updateContact(CONTACT_DTO));
    }
}
