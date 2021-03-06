package doctor.dto.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddressDto {

    private Long id;
    @NotBlank
    @NotNull
    private String street;


    @Pattern(regexp = "^[0-9]{5}(?:-[0-9]{4})?$")
    private String code;

    @NotBlank
    @NotNull
    private String city;
}
