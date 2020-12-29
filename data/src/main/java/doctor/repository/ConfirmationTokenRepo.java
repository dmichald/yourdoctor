package doctor.repository;

import doctor.models.security.ConfirmationToken;
import doctor.models.security.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepo extends CrudRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(final String token);

    Optional<ConfirmationToken> findByUser(final User user);
}
