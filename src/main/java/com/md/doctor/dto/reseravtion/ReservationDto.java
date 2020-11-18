package com.md.doctor.dto.reseravtion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.md.doctor.dto.patient.PatientDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
 public class ReservationDto {

    private Long id;
    private PatientDto patient;


    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @FutureOrPresent
    private LocalTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Future
    private LocalTime endTime;

    private boolean canceled;

}

class LocalDateTimeDeserializer extends JsonDeserializer<LocalTime> {

    @Override
    public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String[] time = jsonParser.getText().split(":");
        return LocalTime.of(Integer.parseInt(time[0]), Integer.parseInt(time[1]));
    }
}
