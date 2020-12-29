package doctor.mapper;

import doctor.dto.reseravtion.ReservationDto;
import doctor.models.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Time;
import java.time.LocalTime;

@Mapper
public interface ReservationMapper {
    @Mapping(target = "createdAt", ignore = true)
    Reservation mapToReservation(ReservationDto reservationDto);

    ReservationDto mapToReservationDto(Reservation reservation);

    default Time map(LocalTime localTime) {
        return Time.valueOf(localTime);
    }

    default LocalTime map(Time time) {
        return time.toLocalTime();
    }

}
