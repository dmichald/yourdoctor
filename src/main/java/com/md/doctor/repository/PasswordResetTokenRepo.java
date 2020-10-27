package com.md.doctor.repository;

import com.md.doctor.models.security.PasswordResetToken;
import com.md.doctor.models.security.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PasswordResetTokenRepo extends CrudRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByUser(User user);

    Optional<PasswordResetToken> findByToken(String token);
}
