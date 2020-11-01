package com.md.doctor;

import com.md.doctor.models.security.Role;
import com.md.doctor.models.security.User;
import com.md.doctor.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {
    private final UserRepo userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void run(String... args)  {
        Role role = new Role();
        role.setName("ROLE_DOCTOR");

        User user = new User();
        user.setEnabled(true);
        user.setEmail("test@Test.pl");
        user.setPassword(passwordEncoder.encode("test"));
        user.setUsername("test");
        user.getRoles().add(role);
        user.addRole(role);
        userRepository.save(user);
    }
}
