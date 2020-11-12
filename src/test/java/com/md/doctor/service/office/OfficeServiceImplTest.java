package com.md.doctor.service.office;

import com.md.doctor.TestResource;
import com.md.doctor.models.security.User;
import com.md.doctor.repository.*;
import com.md.doctor.service.doctor.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
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


    private  OfficeService officeService;

    @BeforeEach
    void setUp() {
        officeService = new OfficeServiceImpl(officeRepository,
                doctorService,
                contactRepository,
                addressRepo,
                specializationRepo);
    }

    @Test
    @DisplayName("correct save office")
    void saveOffice() {
        //given
        User applicationUser = mock(User.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        //when
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(applicationUser);
        officeService.saveOffice(TestResource.OFFICE_DTO);

        //then
        verify(officeRepository,times(1)).save(any());
        verify(doctorService,times(1)).saveDoctor(any());
        verify(contactRepository,times(1)).save(any());
        verify(contactRepository,times(1)).save(any());

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