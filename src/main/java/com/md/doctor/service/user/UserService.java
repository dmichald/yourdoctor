package com.md.doctor.service.user;

import com.md.doctor.dto.security.ResetPasswordDto;
import com.md.doctor.dto.security.UserDto;
import com.md.doctor.models.security.User;

public interface UserService {
    User registerNewUserAccount(UserDto accountDto);

    void deleteUser(User user);

    void changeUserPassword(ResetPasswordDto resetPasswordDto);

    String validateVerificationToken(String token);

    void confirmAccount(String token);

    void sendResetPasswordToken(String email);


}
