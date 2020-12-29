package doctor.dto.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResetPasswordDto {

    private String token;

    @Length(min = 6, message = "Password must have minimum length 6")
    private String password;
}
