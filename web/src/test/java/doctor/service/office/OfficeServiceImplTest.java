package doctor.service.office;


import doctor.dto.office.AddOfficeDto;
import doctor.repository.*;
import doctor.service.doctor.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static doctor.TestResource.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Disabled
@ExtendWith(SpringExtension.class)
@DisplayName("OfficeServiceImpl should")
class OfficeServiceImplTest {
    @MockBean
    private OfficeRepo officeRepository;
    @MockBean
    private DoctorRepo doctorRepository;

    @MockBean
    private ContactRepo contactRepository;

    @MockBean
    private DoctorService doctorService;

    @MockBean
    private AddressRepo addressRepo;

    @MockBean
    private SpecializationRepo specializationRepo;

    @MockBean
    private UserRepo userRepository;


    private OfficeService officeService;

    @BeforeEach
    void setUp() {
        officeService = new OfficeServiceImpl(officeRepository,
                doctorService,
                contactRepository,
                addressRepo,
                specializationRepo,
                userRepository);
    }

    @Test
    @DisplayName("correct save office")
    void saveOffice() {
        //given
        //when
        when(userRepository.findById(1L)).thenReturn(Optional.of(USER));
        officeService.saveOffice(addOfficeDto(), 1L);

        //then
        verify(officeRepository, times(1)).save(any());
        verify(doctorService, times(1)).saveDoctor(any());
        verify(contactRepository, times(1)).save(any());

    }

    private AddOfficeDto addOfficeDto() {
        return new AddOfficeDto(ADDRESS_DTO, PRICE, LocalTime.of(8, 0), LocalTime.of(16, 0),
                30, ADD_DOCTOR_DTO, CONTACT_DTO);
    }

    @Test
    @DisplayName("should return list of cities where offices works ")
    void getCities() {
        //given
        List<String> cities = List.of("Warsaw", " New York", "Zbąszynek");

        //when
        when(officeRepository.getCities()).thenReturn(cities);
        List<String> result = officeService.getCities();

        //then
        assertAll(
                () -> assertEquals(cities.size(), result.size()),
                () -> assertEquals(cities, result)
        );

    }
}
