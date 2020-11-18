package com.md.doctor.service.reservation;

import com.md.doctor.dto.reseravtion.ReservationDto;
import com.md.doctor.exception.EntityNotFoundException;
import com.md.doctor.mapper.PatientMapper;
import com.md.doctor.mapper.ReservationMapper;
import com.md.doctor.models.Office;
import com.md.doctor.models.Patient;
import com.md.doctor.models.Reservation;
import com.md.doctor.repository.OfficeRepo;
import com.md.doctor.repository.PatientRepo;
import com.md.doctor.repository.ReservationRepo;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import static com.md.doctor.exception.EntityNotFoundExceptionMessage.*;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepo reservationRepository;
    private final OfficeRepo officeRepository;
    private final PatientRepo patientRepository;
    private static final PatientMapper patientMapper = Mappers.getMapper(PatientMapper.class);
    private static final ReservationMapper reservationMapper = Mappers.getMapper(ReservationMapper.class);

    public ReservationServiceImpl(ReservationRepo reservationRepository,
                                  OfficeRepo officeRepository,
                                  PatientRepo patientRepository) {
        this.reservationRepository = reservationRepository;
        this.officeRepository = officeRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional
    @Override
    public void saveReservation(ReservationDto reservationDto, Long officeId) {
        Patient patient = patientRepository.save(patientMapper.mapToPatient(reservationDto.getPatient()));

        Office office = officeRepository.findById(officeId).orElseThrow(() -> new EntityNotFoundException(OFFICE_NOT_FOUND(officeId)));
        Reservation reservation = new Reservation();
        reservation.setDate(Date.valueOf(reservationDto.getDate()));
        reservation.setStartTime(Time.valueOf(reservationDto.getStartTime()));
        reservation.setEndTime(Time.valueOf(reservationDto.getEndTime()));
        reservation.addOffice(office);
        reservation.setCanceled(false);

        reservation.addPatient(patient);

        reservationRepository.save(reservation);
    }


    @Override
    public ReservationDto getReservationById(Long id) {
        return reservationRepository.findById(id)
                .map(reservationMapper::mapToReservationDto)
                .orElseThrow(() -> new EntityNotFoundException("RESERVATION WITH GIVEN ID NOT EXIST. ID: " + id));
    }

    @Override
    @Transactional
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.
                findById(reservationId).orElseThrow(() -> new EntityNotFoundException("RESERVATION WITH GIVEN ID NOT EXIST. ID: " + reservationId));

        reservation.setCanceled(true);
    }

    @Override
    public List<ReservationDto> getReservationByDayAndDoctor(Date date, Office office) {
        return null;
    }

    @Override
    public ReservationDto getReservationByDateAndStartTimeAndEndTime(Date date, Time startTime, Time endTime) {
        return null;
    }
}
