package com.md.doctor.service.reservation;

import com.md.doctor.dto.ReservationDto;
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
    public void saveReservation(ReservationDto reservationDto) {
        Patient patient = patientRepository.save(patientMapper.mapToPatient(reservationDto.getPatient()));

        Office office = officeRepository.findById(reservationDto.getOffice().getId())
                .orElseThrow(() -> new EntityNotFoundException("OFFICE WITH GIVEN ID NOT EXIST. ID: " + reservationDto.getOffice().getId()));

        Reservation reservation = new Reservation();
        reservation.setDate(reservationDto.getDate());
        reservation.setStartTime(reservationDto.getStartTime());
        reservation.setEndTime(reservationDto.getEndTime());
        reservation.setCanceled(false);

        reservation.addOffice(office);
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
}
