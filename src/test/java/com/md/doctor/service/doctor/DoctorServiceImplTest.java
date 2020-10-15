package com.md.doctor.service.doctor;

import com.md.doctor.dto.doctordto.AddDoctorDto;
import com.md.doctor.dto.doctordto.GetDoctorDto;
import com.md.doctor.exception.EntityNotFoundException;
import com.md.doctor.models.Doctor;
import com.md.doctor.repository.DoctorRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("DoctorServiceImpl should")
class DoctorServiceImplTest {

    private static final Pageable PAGEABLE = PageRequest.of(0, 5);
    private static final Long ID = 1L;
    private static final String NAME = "Jan";
    private static final String SURNAME = "Kowal";
    private static final Doctor DOCTOR = new Doctor(ID, NAME, SURNAME, null);
    private static final Page<Doctor> DOCTOR_PAGE = new PageImpl<>(Collections.singletonList(DOCTOR), PAGEABLE, 3);
    private static final GetDoctorDto DOCTOR_DTO = new GetDoctorDto(ID, NAME, SURNAME, null);
    private static final AddDoctorDto ADD_DOCTOR_DTO = new AddDoctorDto(NAME, SURNAME, null);
    private static final Page<GetDoctorDto> EXPECTED_PAGE = new PageImpl<>(Collections.singletonList(DOCTOR_DTO), PAGEABLE, 3);
    @Mock
    private DoctorRepo doctorRepository;

    private DoctorServiceImpl doctorService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        doctorService = new DoctorServiceImpl(doctorRepository);
    }

    @Test
    @DisplayName("get doctor by given id")
    void getDoctorById() {
        //given
        Optional<Doctor> op = Optional.of(DOCTOR);


        //when
        when(doctorRepository.findById(1L)).thenReturn(op);

        //then
        GetDoctorDto returnedDto = doctorService.getDoctorById(ID);
        assertEquals(DOCTOR_DTO.getName(), returnedDto.getName());
        verify(doctorRepository, times(1)).findById(ID);

    }

    @Test
    @DisplayName("should throw EntityNotFoundException when doctor not found")
    void throeEntityNotFoundException() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> doctorService.getDoctorById(anyLong()));
    }

    @Test
    void getDoctorsByCategory() {
        //given

        //when
        when(doctorRepository.findBySpecializationId(ID, PAGEABLE)).thenReturn(DOCTOR_PAGE);

        //then
        Page<GetDoctorDto> returnedPage = doctorService.getDoctorsByCategory(ID, PAGEABLE);
        assertEquals(EXPECTED_PAGE, returnedPage);

    }

    @Test
    void getDoctorByNameOrSurname() {
        //given

        //when
        when(doctorRepository.findAllByNameContainingOrSurnameContaining(NAME, SURNAME, PAGEABLE)).thenReturn(DOCTOR_PAGE);

        //then
        Page<GetDoctorDto> returnedPage = doctorService.getDoctorByNameOrSurname(NAME, SURNAME, PAGEABLE);

        assertEquals(EXPECTED_PAGE, returnedPage);
    }

    @Test
    void testSaveDoctor() {
        doctorService.saveDoctor(ADD_DOCTOR_DTO);
        verify(doctorRepository, times(1)).save(any());
    }
}