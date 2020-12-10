package doctor.repository;

import doctor.models.security.PasswordResetToken;
import doctor.models.security.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PasswordResetTokenRepo extends CrudRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByUser(User user);

    Optional<PasswordResetToken> findByToken(String token);
}
