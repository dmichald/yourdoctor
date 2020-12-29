package doctor.exception;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
class ErrorResponse {

    private String message;
    @Singular
    private Set<String> errors = new HashSet<>();
}
