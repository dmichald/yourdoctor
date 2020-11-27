package com.md.doctor.service.user;

import com.md.doctor.dto.OfficeContext;
import com.md.doctor.dto.security.UserDto;
import com.md.doctor.models.security.ConfirmationToken;
import com.md.doctor.models.security.User;
import com.md.doctor.repository.ConfirmationTokenRepo;
import com.md.doctor.repository.PasswordResetTokenRepo;
import com.md.doctor.repository.RoleRepo;
import com.md.doctor.repository.UserRepo;
import com.md.doctor.service.ConfirmationTokenService;
import com.md.doctor.service.EmailSenderService;
import com.md.doctor.service.office.OfficeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static com.md.doctor.TestResource.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private UserRepo userRepository;

    @MockBean
    private RoleRepo roleRepository;
    @MockBean
    private BCryptPasswordEncoder passwordEncoder;
    @MockBean
    private ConfirmationTokenRepo confirmationTokenRepository;
    @MockBean
    private PasswordResetTokenRepo passwordResetTokenRepository;

    @MockBean
    private OfficeService officeService;

    @MockBean
    private EmailSenderService emailSenderService;
    private ConfirmationTokenService confirmationTokenService;
    @MockBean
    private JavaMailSender mailSender;

    private UserService userService;

    @BeforeEach
    void setUp() {
        confirmationTokenService = new ConfirmationTokenService(confirmationTokenRepository);
        userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder, confirmationTokenRepository,
                passwordResetTokenRepository, emailSenderService, confirmationTokenService, officeService);
    }

    @Test
    void registerNewUserAccount() {
        //given
        OfficeContext officeContext = new OfficeContext(OFFICE_DTO, new UserDto());
        //when
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(USER));
        when(userRepository.save(any())).thenReturn(USER);
        userService.registerNewUserAccount(officeContext);

        //then
        verify(emailSenderService, times(1)).sendEmail(any());
    }

    @Test
    void deleteUser() {
        //when
        userService.deleteUser(any());

        //then
        verify(userRepository, times(1)).delete(any());
    }

    @Test
    void test_TOKEN_INVALID() {
        when(confirmationTokenRepository.findByToken(TOKEN)).thenReturn(Optional.empty());

        String result = userService.validateVerificationToken(TOKEN);

        assertEquals(UserServiceImpl.TOKEN_INVALID, result);

    }

    @Test
    void test_TOKEN_EXPIRED() {
        ConfirmationToken confirmationToken = new ConfirmationToken(TOKEN, USER);
        confirmationToken.setExpiryDate(Date.valueOf(LocalDate.of(2002, 12, 2)));

        when(confirmationTokenRepository.findByToken(TOKEN)).thenReturn(Optional.of(confirmationToken));

        String result = userService.validateVerificationToken(TOKEN);

        assertEquals(UserServiceImpl.TOKEN_EXPIRED, result);

    }

    @Test
    void confirmAccount() {
        //given
        User user = new User();
        user.setEnabled(false);
        ConfirmationToken confirmationToken = new ConfirmationToken(TOKEN, user);
        when(confirmationTokenRepository.findByToken(TOKEN)).thenReturn(Optional.of(confirmationToken));

        //when
        userService.confirmAccount(TOKEN);

        //then
        assertTrue(confirmationToken.getUser().isEnabled());
    }

    @Test
    void sendResetPasswordToken() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(USER));

        userService.sendResetPasswordToken(EMAIL);

        verify(emailSenderService, times(1)).sendEmail(any());
    }
}
