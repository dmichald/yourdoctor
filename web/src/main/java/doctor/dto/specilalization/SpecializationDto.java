package doctor.dto.specilalization;

import doctor.models.Specialization;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SpecializationDto {
    private Long id;
    private String name;

    public SpecializationDto(Specialization specialization) {
        this.id = specialization.getId();
        this.name = specialization.getName();
    }
}
