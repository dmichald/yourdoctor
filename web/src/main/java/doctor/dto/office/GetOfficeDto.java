package doctor.dto.office;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class GetOfficeDto {
    Long id;
    String name;
    String specializationName;
}
