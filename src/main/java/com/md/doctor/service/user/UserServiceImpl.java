package com.md.doctor.service.user;

import com.md.doctor.dto.OfficeContext;
import com.md.doctor.dto.security.ResetPasswordDto;
import com.md.doctor.dto.security.UserDto;
import com.md.doctor.exception.EntityNotFoundException;
import com.md.doctor.exception.UserAlreadyExistException;
import com.md.doctor.models.security.ConfirmationToken;
import com.md.doctor.models.security.PasswordResetToken;
import com.md.doctor.models.security.User;
import com.md.doctor.repository.ConfirmationTokenRepo;
import com.md.doctor.repository.PasswordResetTokenRepo;
import com.md.doctor.repository.RoleRepo;
import com.md.doctor.repository.UserRepo;
import com.md.doctor.service.ConfirmationTokenService;
import com.md.doctor.service.EmailSenderService;
import com.md.doctor.service.office.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
     static final String TOKEN_INVALID = "invalidToken";
     static final String TOKEN_EXPIRED = "expired";
     static final String TOKEN_VALID = "valid";

    private final UserRepo userRepository;
    private final RoleRepo roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConfirmationTokenRepo confirmationTokenRepository;
    private final PasswordResetTokenRepo passwordResetTokenRepository;
    private final EmailSenderService emailSenderService;
    private final ConfirmationTokenService confirmationTokenService;
    private final OfficeService officeService;

    @Override
    public User registerNewUserAccount(OfficeContext officeContext) {
        UserDto accountDto = officeContext.getOwner();
        if (emailExists(accountDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + accountDto.getEmail());
        }
        final User user = new User();

        user.setUsername(accountDto.getUsername());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        user.setRoles(Collections.singleton(roleRepository.findByName("ROLE_DOCTOR")));
        User savedUser = userRepository.save(user);

        officeService.saveOffice(officeContext.getOffice(), savedUser.getId());

        ConfirmationToken confirmationToken = new ConfirmationToken(UUID.randomUUID().toString(), savedUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        sendConfirmationEmail(accountDto.getEmail(), confirmationToken.getToken());

        return savedUser;
    }

    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email).isPresent();
    }


    @Override
    public void deleteUser(User user) {
        final Optional<ConfirmationToken> verificationToken = confirmationTokenRepository.findByUser(user);

        verificationToken.ifPresent(confirmationTokenRepository::delete);

        final Optional<PasswordResetToken> passwordToken = passwordResetTokenRepository.findByUser(user);

        passwordToken.ifPresent(passwordResetTokenRepository::delete);

        userRepository.delete(user);
    }

    @Override
    public void changeUserPassword(ResetPasswordDto resetPasswordDto) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(resetPasswordDto.getToken())
                .orElseThrow(() -> new EntityNotFoundException("TOKEN NOT EXIST"));

        passwordResetToken.getUser().setPassword(passwordEncoder.encode(resetPasswordDto.getPassword()));
    }

    @Override
    public String validateVerificationToken(String token) {
        final Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenRepository.findByToken(token);
        final ConfirmationToken confirmationToken;
        if (optionalConfirmationToken.isEmpty()) {
            return TOKEN_INVALID;
        } else {
            confirmationToken = optionalConfirmationToken.get();
        }

        final Calendar cal = Calendar.getInstance();
        if ((confirmationToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            confirmationTokenRepository.delete(confirmationToken);
            return TOKEN_EXPIRED;
        }

        return TOKEN_VALID;
    }


    private void sendConfirmationEmail(String userMail, String token) {

        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject("Mail Confirmation Link!");
        mailMessage.setFrom("<MAIL>");
        mailMessage.setText(
                "Thank you for registering. Please click on the below link to activate your account." + "http://localhost:8080/confirm?token="
                        + token);

        emailSenderService.sendEmail(mailMessage);
    }

    @Override
    public void confirmAccount(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("TOKEN NOT EXIST"));

        confirmationToken.getUser().setEnabled(true);
    }

    @Override
    public void sendResetPasswordToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("USER NOT FOUND"));

        PasswordResetToken passwordResetToken = new PasswordResetToken(UUID.randomUUID().toString(), user);

        passwordResetTokenRepository.save(passwordResetToken);
        sendResetToken(email, passwordResetToken.getToken());
    }

    private void sendResetToken(String userMail, String token) {

        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject("Reset Password!");
        mailMessage.setFrom("<MAIL>");
        mailMessage.setText(
                "To reset password click that link: ." + "http://localhost:8080/confirm?token="
                        + token);

        emailSenderService.sendEmail(mailMessage);
    }

}
