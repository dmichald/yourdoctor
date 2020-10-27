package com.md.doctor.service;

import com.md.doctor.models.security.ConfirmationToken;
import com.md.doctor.repository.ConfirmationTokenRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepo confirmationTokenRepository;

    public ConfirmationToken saveConfirmationToken(ConfirmationToken confirmationToken) {
        return confirmationTokenRepository.save(confirmationToken);
    }

    void deleteConfirmationToken(Long id) {
        confirmationTokenRepository.deleteById(id);
    }
}
