package doctor.dto.office;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import doctor.dto.LocalTimeDeserializer;
import doctor.dto.address.AddressDto;
import doctor.dto.contact.ContactDto;
import doctor.dto.doctor.AddDoctorDto;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddOfficeDto {
    @NotNull
    AddressDto address;
    @Min(value = 1L, message = "Price cannot be less than 1")
    @Max(value = 1000L, message = "Price for visit cannot be bigger than 1000")
    int price;
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    LocalTime startWorkAt;
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    LocalTime finishWorkAt;
    @Min(value = 10L, message = "Visit duration cannot be shorter than 10 minutes")
    @Max(value = 60L, message = "Visit duration cannot be longer than 60 minutes")
    int oneVisitDuration;
    @NotNull
    private AddDoctorDto doctor;
    @NotNull
    private ContactDto contact;
}
