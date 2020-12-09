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
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.md.doctor.exception.EntityNotFoundExceptionMessage.OFFICE_NOT_FOUND;
import static com.md.doctor.exception.EntityNotFoundExceptionMessage.RESERVATION_NOT_FOUND;

@Service
@Slf4j
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
        reservation.addOffice(office);
        reservation.setCanceled(false);

        reservation.addPatient(patient);

        Reservation saved = reservationRepository.save(reservation);
        log.debug("Saved reservation with id: " + saved.getId());
    }


    @Override
    public ReservationDto getReservationById(Long id) {
        return reservationRepository.findById(id)
                .map(reservationMapper::mapToReservationDto)
                .orElseThrow(() -> new EntityNotFoundException(RESERVATION_NOT_FOUND(id)));
    }

    @Override
    @Transactional
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.
                findById(reservationId).orElseThrow(() -> new EntityNotFoundException(RESERVATION_NOT_FOUND(reservationId)));

        reservation.setCanceled(true);
    }

    @Override
    public List<String> getFreeReservationsHours(Date date, Long officeId) {
        Office office = getOffice(officeId);
        var reservations = reservationRepository.findAllByOfficeAndDate(office, date);
        var allHours = getAllReservationsAvailableInOneDay(office);
        List<String> freeHours = new ArrayList<>();

        allHours.forEach(hour -> {
            if (isGivenHourAvailable(hour, reservations, office.getOneVisitDuration())) {
                freeHours.add(hour.toString());
            }
        });

        ArrayList<String> toRemove = new ArrayList<>();
        if (date.toLocalDate().equals(LocalDate.now())) {
            LocalTime now = LocalTime.now();
            log.debug("IN");
            for (String freeHour : freeHours) {
                String[] split = freeHour.split(":");
                LocalTime hour = LocalTime.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]));

                if (hour.isBefore(now)) {
                    log.debug("REMOVED " + freeHour);
                    log.debug(freeHours.toString());

                    toRemove.add(freeHour);
                }
            }
            freeHours.removeAll(toRemove);
        }



        return freeHours;
    }

    @Override
    public Map<LocalDate, List<ReservationDto>> getReservationFromTo(LocalDate from, int to, Long officeId) {
        Date fromSqlDate = Date.valueOf(from);
        Date endDate = Date.valueOf(from.plusDays(to));
        List<ReservationDto> reservationDtoList = reservationRepository.findAllByDateBetweenAndOffice(fromSqlDate, endDate, getOffice(officeId))
                .stream()
                .map(reservationMapper::mapToReservationDto)
                .collect(Collectors.toList());

        Map<LocalDate, List<ReservationDto>> unsoretdMap = reservationDtoList.stream()
                .collect(Collectors.groupingBy(ReservationDto::getDate));

        //sort lists in map
        unsoretdMap.values().forEach(list -> list.sort(Comparator.comparing(ReservationDto::getStartTime)));

        //return sorted map (asc)
        return new TreeMap<>(unsoretdMap);

    }


    private boolean isGivenHourAvailable(LocalTime toCheck, List<Reservation> reservations, int visitDuration) {
        for (Reservation reservation : reservations) {
            LocalTime start = reservation.getStartTime().toLocalTime();
            LocalTime toCheckEnd = toCheck.plusMinutes(visitDuration);
            LocalTime end = start.plusMinutes(visitDuration);
            if (toCheck.equals(start) || (toCheck.isAfter(start) && toCheck.isBefore(end)) || (toCheckEnd.isAfter(start) && toCheckEnd.isBefore(end))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns all available hours in a given office in given day.
     * For example: if the office works from 8 a.m to 16 (8 a.m) and  the duration of a visit is 30 minutes
     * the day will be divided into 16 parts and each next visit hour will be 8:30, 9:00,9:30
     *
     * @param office office in which hours should be searching
     * @return list of all available reservations hours in given office
     */
    private List<LocalTime> getAllReservationsAvailableInOneDay(Office office) {
        final int hour = 60;
        LocalTime startTime = office.getStartWorkAt().toLocalTime();
        LocalTime endTime = office.getFinishWorkAt().toLocalTime();

        Duration totalTime = Duration.between(startTime, endTime);
        int subIntervalsCount = (endTime.getHour() - startTime.getHour()) * (hour / office.getOneVisitDuration());
        Duration subIntervalLength = totalTime.dividedBy(subIntervalsCount);

        List<LocalTime> hours = new ArrayList<>();

        LocalTime time = startTime;
        for (int i = 0; i < subIntervalsCount; i++) {
            hours.add(time);
            time = time.plus(subIntervalLength);
        }

        return hours;
    }

    private Office getOffice(Long id) {
        return officeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(OFFICE_NOT_FOUND(id)));
    }
}
