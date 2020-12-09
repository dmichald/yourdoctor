package com.md.doctor.controller;

import com.md.doctor.dto.reseravtion.ReservationDto;
import com.md.doctor.service.reservation.ReservationService;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/offices/{officeId}/reservations")
    public void addReservation(@PathVariable Long officeId, @Valid @RequestBody ReservationDto reservationDto) {
        reservationService.saveReservation(reservationDto, officeId);
    }

    @GetMapping("/offices/{officeId}/available-hours")
    ListHoursWrapper getAvailableHoursInGivenDay(@PathVariable Long officeId, @RequestParam Date date) {
        var hours = reservationService.getFreeReservationsHours(date, officeId);
        return new ListHoursWrapper(hours);
    }

    @GetMapping("/offices/{officeId}/reservations")
    Map<LocalDate, List<ReservationDto>> getReservationFromTo(@RequestParam Date start, @RequestParam Integer offset, @RequestParam Long officeId) {
        return reservationService.getReservationFromTo(start.toLocalDate(), offset, officeId);
    }

    @PutMapping("/reservations")
    void cancelReservation(@RequestParam Long id) {
        reservationService.cancelReservation(id);
    }


}

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
class ListHoursWrapper {
    private List<String> hours;
}
