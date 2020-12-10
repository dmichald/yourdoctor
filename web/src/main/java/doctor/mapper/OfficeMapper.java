package doctor.mapper;

import doctor.dto.office.OfficeDetails;
import doctor.dto.office.OfficeDto;
import doctor.models.Office;
import org.mapstruct.Mapper;

import java.sql.Time;
import java.time.LocalTime;

@Mapper
public interface OfficeMapper {
    Office mapToOffice(OfficeDto officeDto);

    OfficeDto mapToOfficeDto(Office office);

    OfficeDetails mapToOfficeDetails(Office office);

    default LocalTime map(Time time) {
        return time.toLocalTime();
    }

    default Time map(LocalTime localTime) {
        return Time.valueOf(localTime);
    }

}
