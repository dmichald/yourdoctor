package com.md.doctor.service.reservation;

import com.md.doctor.dto.reseravtion.ReservationDto;
import com.md.doctor.mapper.PatientMapper;
import com.md.doctor.mapper.ReservationMapper;
import com.md.doctor.models.Reservation;
import com.md.doctor.repository.OfficeRepo;
import com.md.doctor.repository.PatientRepo;
import com.md.doctor.repository.ReservationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.md.doctor.TestResource.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("ReservationServiceImpl should ")
class ReservationServiceImplTest {
    @MockBean
    private ReservationRepo reservationRepository;
    @MockBean
    private OfficeRepo officeRepository;
    @MockBean
    private PatientRepo patientRepository;
    private static final PatientMapper patientMapper = Mappers.getMapper(PatientMapper.class);
    private static final ReservationMapper reservationMapper = Mappers.getMapper(ReservationMapper.class);
    private static ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationServiceImpl(reservationRepository, officeRepository, patientRepository);
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
}