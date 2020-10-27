package com.md.doctor.repository;

import com.md.doctor.models.security.ConfirmationToken;
import com.md.doctor.models.security.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepo extends CrudRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(final String token);

    Optional<ConfirmationToken> findByUser(final User user);
}
