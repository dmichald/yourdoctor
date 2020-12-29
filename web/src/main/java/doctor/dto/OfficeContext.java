package doctor.dto;

import doctor.dto.office.AddOfficeDto;
import doctor.dto.security.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OfficeContext {
    @NotNull
    private AddOfficeDto office;

    @NotNull
    private UserDto owner;
}
