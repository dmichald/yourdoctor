package doctor.dto.doctor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddDoctorDto {
    @NotBlank
    @NotNull

    private String name;

    @NotBlank
    @NotNull
    private String surname;

    private Set<Long> specializationSet = new HashSet<>();
}
