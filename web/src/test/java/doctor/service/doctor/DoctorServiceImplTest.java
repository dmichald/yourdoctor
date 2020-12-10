package doctor.service.doctor;


import doctor.dto.doctor.AddDoctorDto;
import doctor.dto.doctor.GetDoctorDto;
import doctor.dto.doctor.UpdateDoctorNameAndSurnameDto;
import doctor.exception.EntityNotFoundException;
import doctor.models.Doctor;
import doctor.models.Specialization;
import doctor.repository.DoctorRepo;
import doctor.repository.SpecializationRepo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static doctor.TestResource.SPECIALIZATION;
import static doctor.exception.EntityNotFoundExceptionMessage.DOCTOR_NOT_FOUND;
import static doctor.exception.EntityNotFoundExceptionMessage.SPECIALIZATION_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Disabled
@ExtendWith(SpringExtension.class)
@DisplayName("DoctorServiceImpl should")
class DoctorServiceImplTest {

    private static final Pageable PAGEABLE = PageRequest.of(0, 5);
    private static final Long ID = 1L;
    private static final String NAME = "Jan";
    private static final String SURNAME = "Kowal";
    private static final Doctor DOCTOR = new Doctor(ID, NAME, SURNAME, new HashSet<>());
    private static final Page<Doctor> DOCTOR_PAGE = new PageImpl<>(Collections.singletonList(DOCTOR), PAGEABLE, 3);
    private static final GetDoctorDto DOCTOR_DTO = new GetDoctorDto(ID, NAME, SURNAME, new HashSet<>());
    private static final AddDoctorDto ADD_DOCTOR_DTO = new AddDoctorDto(NAME, SURNAME, null);
    private static final Page<GetDoctorDto> EXPECTED_PAGE = new PageImpl<>(Collections.singletonList(DOCTOR_DTO), PAGEABLE, 3);
    @Mock
    private DoctorRepo doctorRepository;

    @Mock
    private SpecializationRepo specializationRepo;

    private DoctorServiceImpl doctorService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        doctorService = new DoctorServiceImpl(doctorRepository, specializationRepo);
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
        when(doctorRepository.save(DOCTOR)).thenReturn(DOCTOR);
        doctorService.saveDoctor(ADD_DOCTOR_DTO);
        verify(doctorRepository, times(1)).save(DOCTOR);
    }

    @Test
    @DisplayName(" should correct add specialization to existing doctor")
    void addSpecializationTest() {

        //when
        when(specializationRepo.findById(ID)).thenReturn(Optional.of(SPECIALIZATION));
        when(doctorRepository.findById(ID)).thenReturn(Optional.of(DOCTOR));

        doctorService.addSpecialization(ID, ID);

        //then
        Doctor doctor = getDoctor();
        Specialization specialization = getSpecialization();

        assertTrue(doctor.getSpecializations().contains(specialization));
    }

    @Test
    @DisplayName(" should throw exception during adding specialization if doctor not exists")
    void addSpecializationDoctorNotFoundExceptionTest() {
        //when
        when(doctorRepository.findById(ID)).thenReturn(Optional.empty());

        //then
        Throwable exception = Assertions.assertThrows(EntityNotFoundException.class, () -> doctorService.addSpecialization(ID, ID));
        assertEquals(DOCTOR_NOT_FOUND(ID), exception.getMessage());
    }

    @Test
    @DisplayName("should correct remove specialization")
    void removeSpecializationTest() {
        //when
        when(specializationRepo.findById(ID)).thenReturn(Optional.of(SPECIALIZATION));
        when(doctorRepository.findById(ID)).thenReturn(Optional.of(DOCTOR));

        doctorService.addSpecialization(ID, ID);
        doctorService.removeSpecialization(ID, ID);

        //then
        Doctor doctor = getDoctor();
        assertTrue(doctor.getSpecializations().isEmpty());
    }

    @Test
    @DisplayName("should update doctor name and surname")
    void updateDoctorNameAndSurnameTest() {
        //given
        UpdateDoctorNameAndSurnameDto updateDoc = new UpdateDoctorNameAndSurnameDto("Nowy", "New");
        //when
        when(doctorRepository.findById(ID)).thenReturn(Optional.of(DOCTOR));
        Doctor doctor = doctorService.updateDoctorNameAndSurname(ID, updateDoc);

        //then
        assertAll(
                () -> assertEquals(doctor.getName(), updateDoc.getName()),
                () -> assertEquals(doctor.getSurname(), updateDoc.getSurname())
        );
    }

    private Doctor getDoctor() {
        return doctorRepository.findById(ID)
                .orElseThrow(() -> new EntityNotFoundException(DOCTOR_NOT_FOUND(ID)));
    }

    private Specialization getSpecialization() {
        return specializationRepo
                .findById(ID).orElseThrow(() -> new EntityNotFoundException(SPECIALIZATION_NOT_FOUND(ID)));
    }
}
