package com.md.doctor.service.user;

import com.md.doctor.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) {

        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("USERNAME NOT FOUND: " + userName));
    }
}
