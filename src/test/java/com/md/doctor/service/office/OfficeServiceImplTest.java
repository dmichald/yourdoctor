package com.md.doctor.service.office;

import com.md.doctor.TestResource;
import com.md.doctor.dto.OfficeDto;
import com.md.doctor.repository.ContactRepo;
import com.md.doctor.repository.DoctorRepo;
import com.md.doctor.repository.OfficeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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


    private  OfficeService officeService;

    @BeforeEach
    void setUp() {
       /* officeService = new OfficeServiceImpl(officeRepository,
                doctorRepository,
                contactRepository);*/
    }
    @Disabled
    @Test
    @DisplayName("correct save office")
    void saveOffice() {
        //when
        officeService.saveOffice(TestResource.OFFICE_DTO);

        //then
        verify(officeRepository,times(1)).save(any());
        verify(doctorRepository,times(1)).save(any());
        verify(contactRepository,times(1)).save(any());

    }
}