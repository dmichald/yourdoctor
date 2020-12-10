package doctor.service.user;

import doctor.dto.OfficeContext;
import doctor.dto.security.ResetPasswordDto;
import doctor.models.security.User;

public interface UserService {
    User registerNewUserAccount(OfficeContext officeContext);

    void deleteUser(User user);

    void changeUserPassword(ResetPasswordDto resetPasswordDto);

    String validateVerificationToken(String token);

    void confirmAccount(String token);

    void sendResetPasswordToken(String email);


}
