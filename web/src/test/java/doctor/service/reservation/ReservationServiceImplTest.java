package doctor.service.reservation;

import com.fasterxml.jackson.core.JsonProcessingException;
import doctor.dto.reseravtion.ReservationDto;
import doctor.mapper.PatientMapper;
import doctor.mapper.ReservationMapper;
import doctor.models.Reservation;
import doctor.repository.OfficeRepo;
import doctor.repository.PatientRepo;
import doctor.repository.ReservationRepo;
import doctor.service.EmailSenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static doctor.TestResource.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Disabled
@ExtendWith(SpringExtension.class)
@DisplayName("ReservationServiceImpl should ")
class ReservationServiceImplTest {
    private static final PatientMapper patientMapper = Mappers.getMapper(PatientMapper.class);
    private static final ReservationMapper reservationMapper = Mappers.getMapper(ReservationMapper.class);
    @MockBean
    private ReservationRepo reservationRepository;
    @MockBean
    private OfficeRepo officeRepository;
    @MockBean
    private PatientRepo patientRepository;
    @MockBean
    private JavaMailSender javaMailSender;
    private ReservationService reservationService;
    private EmailSenderService emailSenderService;

    @BeforeEach
    void setUp() {
        emailSenderService = new EmailSenderService(javaMailSender);
        reservationService = new ReservationServiceImpl(reservationRepository, officeRepository, patientRepository, emailSenderService);
    }

    @Test
    @DisplayName("correct save reservation")
    void saveReservation() {
        //given

        //when
        when(officeRepository.findById(ID)).thenReturn(Optional.of(OFFICE));
        reservationService.saveReservation(RESERVATION_DTO, ID);

        //then
        verify(reservationRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("return reservation by given id")
    void getReservationById() {
        //given
        Optional<Reservation> optional = Optional.of(RESERVATION);

        //when
        when(reservationRepository.findById(ID)).thenReturn(optional);

        //then
        ReservationDto returned = reservationService.getReservationById(ID);
        assertEquals(RESERVATION_DTO.getId(), returned.getId());
    }

    @Test
    @DisplayName("cancel reservation")
    void cancelReservation() {
        //given

        //when
        when(reservationRepository.findById(ID)).thenReturn(Optional.of(RESERVATION));
        reservationService.cancelReservation(ID);

        //then
        Reservation reservation = reservationMapper.mapToReservation(reservationService.getReservationById(ID));
        assertTrue(reservation.isCanceled());
    }

    @Test
    @DisplayName("should return available hours of given office in given day ")
    void getFreeReservationsHoursTest() {

        //given
        Date date = Date.valueOf(LocalDate.of(2020, 1, 1));
        when(officeRepository.findById(ID)).thenReturn(Optional.of(OFFICE));
        when(reservationRepository.findAllByOfficeAndDate(OFFICE, date)).thenReturn(reservations());

        //when
        var hours = reservationService.getFreeReservationsHours(date, ID);

        //then
        reservations().forEach(reservation -> assertFalse(hours.contains(reservation.getStartTime().toString())));
    }

    @Test
    void getReservationsFromTest() throws JsonProcessingException {
        //given
        Date start = Date.valueOf(DATE);
        Date end = Date.valueOf(DATE.plusDays(2));

        //when
        when(reservationRepository.findAllByDateBetweenAndOffice(start, end, OFFICE)).thenReturn(reservations());
        when(officeRepository.findById(ID)).thenReturn(Optional.of(OFFICE));


        Map<LocalDate, List<ReservationDto>> result = reservationService.getReservationFromTo(DATE, 2, 1L);
        System.err.println(objectMapper().writeValueAsString(result));

        verify(reservationRepository, times(1)).findAllByDateBetweenAndOffice(start, end, OFFICE);
        assertEquals(2, result.keySet().size());

    }

    private List<Time> hours() {
        return List.of(
                Time.valueOf("09:00:00"),
                Time.valueOf("09:30:00"),
                Time.valueOf("10:00:00"),
                Time.valueOf("12:30:00"),
                Time.valueOf("15:00:00")
        );
    }

    private List<Reservation> reservations() {
        return List.of(
                new Reservation(1L, OFFICE, PATIENT, Date.valueOf(DATE), hours().get(0), Date.valueOf(DATE), false),
                new Reservation(2L, OFFICE, PATIENT, Date.valueOf(DATE), hours().get(1), Date.valueOf(DATE), false),
                new Reservation(3L, OFFICE, PATIENT, Date.valueOf(DATE), hours().get(2), Date.valueOf(DATE), false),
                new Reservation(4L, OFFICE, PATIENT, Date.valueOf(DATE), hours().get(3), Date.valueOf(DATE), false),
                new Reservation(5L, OFFICE, PATIENT, Date.valueOf(DATE.plusDays(1)), hours().get(4), Date.valueOf(DATE), false)
        );
    }


}
